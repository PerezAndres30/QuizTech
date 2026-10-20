package com.andre.techquiz.presentation.viewmodel

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.andre.techquiz.TechQuizApp
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty

object AppViewModelFactory {

    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                questionRepository = techQuizApp().container.questionRepository
            )
        }
        initializer {
            val handle = createSavedStateHandle()
            val category = handle.get<String>(GameViewModel.ARG_CATEGORY)
                ?.takeIf { it.isNotEmpty() }
                ?.let { Category.valueOf(it) }
            val difficulty = handle.get<String>(GameViewModel.ARG_DIFFICULTY)
                ?.takeIf { it.isNotEmpty() }
                ?.let { Difficulty.valueOf(it) }
            GameViewModel(
                questionRepository = techQuizApp().container.questionRepository,
                historyRepository = techQuizApp().container.historyRepository,
                categoryFilter = category,
                difficultyFilter = difficulty
            )
        }
        initializer {
            val handle = createSavedStateHandle()
            ResultViewModel(
                historyRepository = techQuizApp().container.historyRepository,
                resultId = handle.get<String>(ResultViewModel.ARG_RESULT_ID).orEmpty()
            )

        }
        initializer {
            HistoryViewModel(
                historyRepository = techQuizApp().container.historyRepository
            )
        }
        initializer {
            AddQuestionViewModel(
                questionRepository = techQuizApp().container.questionRepository
            )
        }
    }
}

private fun CreationExtras.techQuizApp(): TechQuizApp =
    this[APPLICATION_KEY] as TechQuizApp