package com.andre.techquiz.data.source

import android.content.Context
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.data.model.GameResult
import com.andre.techquiz.data.model.Question
import org.json.JSONArray
import org.json.JSONObject

class LocalDataSource(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getCustomQuestions(): List<Question> = readList(KEY_CUSTOM_QUESTIONS) { it.toQuestion() }

    fun saveCustomQuestions(questions: List<Question>) =
        writeList(KEY_CUSTOM_QUESTIONS, questions) { it.toJson() }

    fun getHistory(): List<GameResult> = readList(KEY_GAME_HISTORY) { it.toGameResult() }

    fun saveHistory(results: List<GameResult>) =
        writeList(KEY_GAME_HISTORY, results) { it.toJson() }


    private fun <T> readList(key: String, parse: (JSONObject) -> T): List<T> {
        val json = prefs.getString(key, null) ?: return emptyList()
        return try {
            val array = JSONArray(json)
            List(array.length()) { parse(array.getJSONObject(it)) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun <T> writeList(key: String, items: List<T>, toJson: (T) -> JSONObject) {
        val array = JSONArray()
        items.forEach { array.put(toJson(it)) }
        prefs.edit().putString(key, array.toString()).apply()
    }

    private fun Question.toJson() = JSONObject().apply {
        put("id", id)
        put("text", text)
        put("options", JSONArray(options))
        put("correctIndex", correctIndex)
        put("category", category.name)
        put("difficulty", difficulty.name)
        put("isCustom", isCustom)
    }

    private fun JSONObject.toQuestion(): Question {
        val optionsArray = getJSONArray("options")
        return Question(
            id = getString("id"),
            text = getString("text"),
            options = List(optionsArray.length()) { optionsArray.getString(it) },
            correctIndex = getInt("correctIndex"),
            category = Category.valueOf(getString("category")),
            difficulty = Difficulty.valueOf(getString("difficulty")),
            isCustom = getBoolean("isCustom")
        )
    }

    private fun GameResult.toJson() = JSONObject().apply {
        put("id", id)
        put("timestamp", timestamp)
        put("categoryFilter", categoryFilter?.name ?: JSONObject.NULL)
        put("difficultyFilter", difficultyFilter?.name ?: JSONObject.NULL)
        put("totalQuestions", totalQuestions)
        put("correctAnswers", correctAnswers)
        put("score", score)
        put("maxStreak", maxStreak)
    }

    private fun JSONObject.toGameResult() = GameResult(
        id = getString("id"),
        timestamp = getLong("timestamp"),
        categoryFilter = if (isNull("categoryFilter")) null else Category.valueOf(getString("categoryFilter")),
        difficultyFilter = if (isNull("difficultyFilter")) null else Difficulty.valueOf(getString("difficultyFilter")),
        totalQuestions = getInt("totalQuestions"),
        correctAnswers = getInt("correctAnswers"),
        score = getInt("score"),
        maxStreak = getInt("maxStreak")
    )

    private companion object {
        const val PREFS_NAME = "techquiz_prefs"
        const val KEY_CUSTOM_QUESTIONS = "custom_questions"
        const val KEY_GAME_HISTORY = "game_history"
    }
}