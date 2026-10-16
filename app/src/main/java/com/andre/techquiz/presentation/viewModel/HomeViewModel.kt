package com.andre.techquiz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.andre.techquiz.TechQuizApp
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.data.model.Question
import com.andre.techquiz.data.repository.QuestionRepository
import com.andre.techquiz.presentation.screens.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var allQuestions: List<Question> = emptyList()

    init {
        viewModelScope.launch {
            questionRepository.questions.collect { questions ->
                allQuestions = questions
                _uiState.update { it.withAvailableCount() }
            }
        }
    }

    fun onCategorySelected(category: Category?) {
        _uiState.update { it.copy(selectedCategory = category).withAvailableCount() }
    }

    fun onDifficultySelected(difficulty: Difficulty?) {
        _uiState.update { it.copy(selectedDifficulty = difficulty).withAvailableCount() }
    }

    private fun HomeUiState.withAvailableCount(): HomeUiState = copy(
        availableQuestions = allQuestions.count { question ->
            (selectedCategory == null || question.category == selectedCategory) &&
                    (selectedDifficulty == null || question.difficulty == selectedDifficulty)
        }
    )

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as TechQuizApp
                HomeViewModel(app.container.questionRepository)
            }
        }
    }
}