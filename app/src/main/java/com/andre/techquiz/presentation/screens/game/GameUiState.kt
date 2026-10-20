package com.andre.techquiz.presentation.screens.game

import com.andre.techquiz.data.model.Question

enum class AnswerStatus { Pending, Correct, Wrong }

data class GameUiState(
    val currentQuestion: Question? = null,
    val currentIndex: Int = 0,
    val total: Int = 0,
    val selectedOption: Int? = null,
    val answerStatus: AnswerStatus = AnswerStatus.Pending,
    val score: Int = 0,
    val streak: Int = 0,
    val maxStreak: Int = 0,
    val correctAnswers: Int = 0,
    val secondsLeft: Int = 0,
    val isFinished: Boolean = false
)