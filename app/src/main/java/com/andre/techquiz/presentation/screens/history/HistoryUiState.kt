package com.andre.techquiz.presentation.screens.history

import com.andre.techquiz.data.model.GameResult

data class HistoryUiState(
    val results: List<GameResult> = emptyList()
) {
    val totalGames: Int get() = results.size
}