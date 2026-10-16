package com.andre.techquiz.di

import android.content.Context
import com.andre.techquiz.data.repository.HistoryRepository
import com.andre.techquiz.data.repository.QuestionRepository
import com.andre.techquiz.data.source.LocalDataSource
import com.andre.techquiz.data.source.SeedDataSource

class AppContainer(context: Context) {

    private val localDataSource = LocalDataSource(context)
    private val seedDataSource = SeedDataSource()

    val questionRepository = QuestionRepository(seedDataSource, localDataSource)
    val historyRepository = HistoryRepository(localDataSource)
}