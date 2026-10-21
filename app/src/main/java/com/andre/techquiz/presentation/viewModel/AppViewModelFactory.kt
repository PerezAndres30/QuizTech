package com.andre.techquiz.presentation.viewmodel

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.andre.techquiz.TechQuizApp
import com.andre.techquiz.di.AppContainer

object AppViewModelFactory {

    val Factory = viewModelFactory {
        initializer { container().provideHomeViewModel() }
        initializer {
            val handle = createSavedStateHandle()
            container().provideGameViewModel(
                categoryName = handle.get<String>(GameViewModel.ARG_CATEGORY),
                difficultyName = handle.get<String>(GameViewModel.ARG_DIFFICULTY)
            )
        }
        initializer {
            val handle = createSavedStateHandle()
            container().provideResultViewModel(
                resultId = handle.get<String>(ResultViewModel.ARG_RESULT_ID).orEmpty()
            )
        }
        initializer { container().provideHistoryViewModel() }
        initializer { container().provideAddQuestionViewModel() }
    }
}

private fun CreationExtras.container(): AppContainer =
    (this[APPLICATION_KEY] as TechQuizApp).container