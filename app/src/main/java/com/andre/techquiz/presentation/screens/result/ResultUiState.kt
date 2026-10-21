package com.andre.techquiz.presentation.screens.result

import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.CategoryStat
import com.andre.techquiz.data.model.Difficulty

data class ResultUiState(
    val found: Boolean = false,
    val score: Int = 0,
    val correctAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val accuracyPercent: Int = 0,
    val maxStreak: Int = 0,
    val categoryStats: List<CategoryStat> = emptyList(),
    val categoryFilter: Category? = null,
    val difficultyFilter: Difficulty? = null
)