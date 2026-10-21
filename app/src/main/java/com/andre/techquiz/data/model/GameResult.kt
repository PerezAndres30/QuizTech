package com.andre.techquiz.data.model

data class CategoryStat(
    val category: Category,
    val correct: Int,
    val total: Int
)

data class GameResult(
    val id: String,
    val timestamp: Long,
    val categoryFilter: Category?,
    val difficultyFilter: Difficulty?,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val score: Int,
    val maxStreak: Int,
    val categoryStats: List<CategoryStat> = emptyList()
)