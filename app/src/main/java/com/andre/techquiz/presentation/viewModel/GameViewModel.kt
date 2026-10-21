package com.andre.techquiz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.CategoryStat
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.data.model.GameResult
import com.andre.techquiz.data.model.Question
import com.andre.techquiz.data.repository.HistoryRepository
import com.andre.techquiz.data.repository.QuestionRepository
import com.andre.techquiz.presentation.screens.game.AnswerStatus
import com.andre.techquiz.presentation.screens.game.GameUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class GameViewModel(
    questionRepository: QuestionRepository,
    private val historyRepository: HistoryRepository,
    private val categoryFilter: Category?,
    private val difficultyFilter: Difficulty?
) : ViewModel() {

    private val questions: List<Question> = questionRepository.questions.value
        .filter { question ->
            (categoryFilter == null || question.category == categoryFilter) &&
                    (difficultyFilter == null || question.difficulty == difficultyFilter)
        }
        .shuffled()
        .take(GAME_SIZE)

    private val correctByCategory = mutableMapOf<Category, Int>()

    private val _uiState = MutableStateFlow(GameUiState(total = questions.size))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        if (questions.isEmpty()) {
            _uiState.update { it.copy(isFinished = true) }
        } else {
            showQuestion(0)
        }
    }

    fun onOptionSelected(index: Int) {
        timerJob?.cancel()
        registerAnswer(index)
    }

    fun onTimeout() {
        registerAnswer(null)
    }

    fun onNext() {
        val state = _uiState.value
        if (state.answerStatus == AnswerStatus.Pending) return
        val nextIndex = state.currentIndex + 1
        if (nextIndex >= questions.size) finishGame() else showQuestion(nextIndex)
    }

    private fun showQuestion(index: Int) {
        _uiState.update {
            it.copy(
                currentQuestion = questions[index],
                currentIndex = index,
                selectedOption = null,
                answerStatus = AnswerStatus.Pending,
                secondsLeft = TIME_PER_QUESTION
            )
        }
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.secondsLeft > 0) {
                delay(ONE_SECOND_MS)
                _uiState.update { it.copy(secondsLeft = it.secondsLeft - 1) }
            }
            onTimeout()
        }
    }

    private fun registerAnswer(selected: Int?) {
        val state = _uiState.value
        val question = state.currentQuestion ?: return
        if (state.answerStatus != AnswerStatus.Pending) return

        val isCorrect = selected == question.correctIndex
        val newStreak = if (isCorrect) state.streak + 1 else 0
        val streakBonus = if (isCorrect && newStreak >= STREAK_BONUS_FROM) STREAK_BONUS else 0
        val gained = if (isCorrect) question.difficulty.points + streakBonus else 0

        if (isCorrect) {
            correctByCategory[question.category] = (correctByCategory[question.category] ?: 0) + 1
        }

        _uiState.update {
            it.copy(
                selectedOption = selected,
                answerStatus = if (isCorrect) AnswerStatus.Correct else AnswerStatus.Wrong,
                score = it.score + gained,
                streak = newStreak,
                maxStreak = maxOf(it.maxStreak, newStreak),
                correctAnswers = it.correctAnswers + if (isCorrect) 1 else 0
            )
        }
    }

    private fun finishGame() {
        val state = _uiState.value
        if (state.isFinished) return
        timerJob?.cancel()

        val stats = Category.entries.mapNotNull { category ->
            val total = questions.count { it.category == category }
            if (total == 0) null
            else CategoryStat(category, correctByCategory[category] ?: 0, total)
        }

        val result = GameResult(
            id = UUID.randomUUID().toString(),
            timestamp = System.currentTimeMillis(),
            categoryFilter = categoryFilter,
            difficultyFilter = difficultyFilter,
            totalQuestions = state.total,
            correctAnswers = state.correctAnswers,
            score = state.score,
            maxStreak = state.maxStreak,
            categoryStats = stats
        )
        historyRepository.save(result)
        _uiState.update { it.copy(isFinished = true, finishedResultId = result.id) }
    }

    companion object {
        const val ARG_CATEGORY = "category"
        const val ARG_DIFFICULTY = "difficulty"

        private const val GAME_SIZE = 8
        private const val TIME_PER_QUESTION = 20
        private const val STREAK_BONUS_FROM = 3
        private const val STREAK_BONUS = 5
        private const val ONE_SECOND_MS = 1_000L
    }
}