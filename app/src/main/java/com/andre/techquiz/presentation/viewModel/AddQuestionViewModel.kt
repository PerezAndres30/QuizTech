package com.andre.techquiz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.data.model.Question
import com.andre.techquiz.data.repository.QuestionRepository
import com.andre.techquiz.presentation.screens.addquestion.AddQuestionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class AddQuestionViewModel(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddQuestionUiState())
    val uiState: StateFlow<AddQuestionUiState> = _uiState.asStateFlow()

    fun onTextChange(text: String) {
        _uiState.update { it.copy(text = text) }
    }

    fun onOptionChange(index: Int, value: String) {
        _uiState.update { state ->
            state.copy(
                options = state.options.toMutableList().also { it[index] = value }
            )
        }
    }

    fun onCorrectSelected(index: Int) {
        _uiState.update { it.copy(correctIndex = index) }
    }

    fun onCategorySelected(category: Category) {
        _uiState.update { it.copy(category = category) }
    }

    fun onDifficultySelected(difficulty: Difficulty) {
        _uiState.update { it.copy(difficulty = difficulty) }
    }

    fun onSave() {
        val state = _uiState.value
        if (state.saved) return
        if (!state.isValid) {
            _uiState.update { it.copy(showErrors = true) }
            return
        }
        questionRepository.addQuestion(
            Question(
                id = UUID.randomUUID().toString(),
                text = state.text.trim(),
                options = state.options.map { it.trim() },
                correctIndex = state.correctIndex,
                category = state.category,
                difficulty = state.difficulty,
                isCustom = true
            )
        )
        _uiState.update { it.copy(saved = true) }
    }
}