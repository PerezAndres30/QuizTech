package com.andre.techquiz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.andre.techquiz.data.model.GameResult
import com.andre.techquiz.data.repository.HistoryRepository
import com.andre.techquiz.presentation.screens.result.ResultUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResultViewModel(
    historyRepository: HistoryRepository,
    resultId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(buildState(historyRepository.history.value, resultId))
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()

    private fun buildState(history: List<GameResult>, resultId: String): ResultUiState {
        val result = history.firstOrNull { it.id == resultId } ?: return ResultUiState()
        return ResultUiState(
            found = true,
            score = result.score,
            correctAnswers = result.correctAnswers,
            totalQuestions = result.totalQuestions,
            accuracyPercent = if (result.totalQuestions > 0) {
                result.correctAnswers * 100 / result.totalQuestions
            } else 0,
            maxStreak = result.maxStreak,
            categoryStats = result.categoryStats,
            categoryFilter = result.categoryFilter,
            difficultyFilter = result.difficultyFilter
        )
    }

    companion object {
        const val ARG_RESULT_ID = "resultId"
    }
}