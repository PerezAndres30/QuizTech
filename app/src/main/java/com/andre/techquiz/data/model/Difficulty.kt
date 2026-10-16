package com.andre.techquiz.data.model

import androidx.annotation.StringRes
import com.andre.techquiz.R

enum class Difficulty(@StringRes val labelRes: Int, val points: Int) {
    EASY(R.string.difficulty_easy, 10),
    MEDIUM(R.string.difficulty_medium, 20),
    HARD(R.string.difficulty_hard, 30)
}