package com.andre.techquiz.data.model

data class GameResult(
    val id: String,
    val timestamp: Long,
    val categoryFilter: Category?,
    val difficultyFilter: Difficulty?,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val score: Int,
    val maxStreak: Int
)