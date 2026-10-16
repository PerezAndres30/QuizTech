package com.andre.techquiz.presentation.screens.home

import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty

data class HomeUiState(
    val selectedCategory: Category? = null,
    val selectedDifficulty: Difficulty? = null,
    val availableQuestions: Int = 0
)