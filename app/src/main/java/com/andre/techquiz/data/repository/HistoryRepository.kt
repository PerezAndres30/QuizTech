package com.andre.techquiz.data.repository

import com.andre.techquiz.data.model.GameResult
import com.andre.techquiz.data.source.LocalDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryRepository(
    private val localDataSource: LocalDataSource
) {
    private val _history = MutableStateFlow(localDataSource.getHistory())
    val history: StateFlow<List<GameResult>> = _history.asStateFlow()

    fun save(result: GameResult) {
        val updated = (listOf(result) + _history.value).take(MAX_HISTORY)
        localDataSource.saveHistory(updated)
        _history.value = updated
    }

    fun clear() {
        localDataSource.saveHistory(emptyList())
        _history.value = emptyList()
    }

    private companion object {
        const val MAX_HISTORY = 50
    }
}