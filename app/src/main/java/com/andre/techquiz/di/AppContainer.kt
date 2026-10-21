package com.andre.techquiz.di

import android.content.Context
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.data.repository.HistoryRepository
import com.andre.techquiz.data.repository.QuestionRepository
import com.andre.techquiz.data.source.LocalDataSource
import com.andre.techquiz.data.source.SeedDataSource
import com.andre.techquiz.presentation.viewmodel.AddQuestionViewModel
import com.andre.techquiz.presentation.viewmodel.GameViewModel
import com.andre.techquiz.presentation.viewmodel.HistoryViewModel
import com.andre.techquiz.presentation.viewmodel.HomeViewModel
import com.andre.techquiz.presentation.viewmodel.ResultViewModel
class AppContainer(context: Context) {

    private val localDataSource = LocalDataSource(context)
    private val seedDataSource = SeedDataSource()

    val questionRepository = QuestionRepository(seedDataSource, localDataSource)
    val historyRepository = HistoryRepository(localDataSource)

    fun provideHomeViewModel() = HomeViewModel(
        questionRepository = questionRepository
    )

    /** Los filtros llegan como texto desde la ruta de navegación; vacío significa «Todas». */
    fun provideGameViewModel(categoryName: String?, difficultyName: String?) = GameViewModel(
        questionRepository = questionRepository,
        historyRepository = historyRepository,
        categoryFilter = categoryName?.takeIf { it.isNotEmpty() }?.let { Category.valueOf(it) },
        difficultyFilter = difficultyName?.takeIf { it.isNotEmpty() }?.let { Difficulty.valueOf(it) }
    )

    fun provideResultViewModel(resultId: String) = ResultViewModel(
        historyRepository = historyRepository,
        resultId = resultId
    )

    fun provideHistoryViewModel() = HistoryViewModel(
        historyRepository = historyRepository
    )

    fun provideAddQuestionViewModel() = AddQuestionViewModel(
        questionRepository = questionRepository
    )
}