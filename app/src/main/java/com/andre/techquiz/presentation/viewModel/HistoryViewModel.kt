package com.andre.techquiz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andre.techquiz.data.repository.HistoryRepository
import com.andre.techquiz.presentation.screens.history.HistoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            historyRepository.history.collect { results ->
                _uiState.update {
                    HistoryUiState(
                        results = results,
                        bestScore = results.maxOfOrNull { it.score } ?: 0
                    )
                }
            }
        }
    }

    fun onClearHistory() {
        historyRepository.clear()
    }
}