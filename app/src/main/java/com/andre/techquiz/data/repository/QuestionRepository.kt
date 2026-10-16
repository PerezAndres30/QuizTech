package com.andre.techquiz.data.repository

import com.andre.techquiz.data.model.Question
import com.andre.techquiz.data.source.LocalDataSource
import com.andre.techquiz.data.source.SeedDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuestionRepository(
    private val seedDataSource: SeedDataSource,
    private val localDataSource: LocalDataSource
) {
    private val customQuestions = localDataSource.getCustomQuestions()

    private val _questions = MutableStateFlow(seedDataSource.getQuestions() + customQuestions)
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    fun addQuestion(question: Question) {
        val customs = _questions.value.filter { it.isCustom } + question
        localDataSource.saveCustomQuestions(customs)
        _questions.update { it + question }
    }
}