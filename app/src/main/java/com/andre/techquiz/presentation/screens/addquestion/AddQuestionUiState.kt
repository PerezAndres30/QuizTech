package com.andre.techquiz.presentation.screens.addquestion

import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.data.model.Question

data class AddQuestionUiState(
    val text: String = "",
    val options: List<String> = List(Question.OPTIONS_COUNT) { "" },
    val correctIndex: Int = 0,
    val category: Category = Category.entries.first(),
    val difficulty: Difficulty = Difficulty.EASY,
    val showErrors: Boolean = false,
    val saved: Boolean = false
) {
    val isTextBlank: Boolean get() = text.isBlank()

    val hasDuplicateOptions: Boolean
        get() {
            val filled = options.map { it.trim().lowercase() }.filter { it.isNotEmpty() }
            return filled.size != filled.toSet().size
        }

    val isValid: Boolean
        get() = !isTextBlank &&
                options.none { it.isBlank() } &&
                !hasDuplicateOptions
}