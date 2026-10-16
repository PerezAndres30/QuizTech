package com.andre.techquiz.data.model

data class Question(
    val id: String,
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val category: Category,
    val difficulty: Difficulty,
    val isCustom: Boolean = false
) {
    init {
        require(options.size == OPTIONS_COUNT) { "Una pregunta debe tener $OPTIONS_COUNT opciones" }
        require(correctIndex in options.indices) { "correctIndex fuera de rango" }
    }

    companion object {
        const val OPTIONS_COUNT = 4
    }
}