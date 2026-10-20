package com.andre.techquiz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.presentation.screens.game.GameScreen
import com.andre.techquiz.presentation.screens.home.HomeScreen
import com.andre.techquiz.presentation.screens.result.ResultScreen
import com.andre.techquiz.presentation.viewmodel.GameViewModel
import com.andre.techquiz.presentation.viewmodel.ResultViewModel
import com.andre.techquiz.presentation.screens.history.HistoryScreen
import com.andre.techquiz.presentation.screens.addquestion.AddQuestionScreen

object Routes {
    const val HOME = "home"
    const val GAME = "game?category={category}&difficulty={difficulty}"
    const val RESULT = "result/{resultId}"
    const val HISTORY = "history"
    const val ADD_QUESTION = "add_question"

    fun game(category: Category?, difficulty: Difficulty?): String =
        "game?category=${category?.name.orEmpty()}&difficulty=${difficulty?.name.orEmpty()}"

    fun result(resultId: String): String = "result/$resultId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onStartGame = { category, difficulty ->
                    navController.navigate(Routes.game(category, difficulty))
                },
                onOpenHistory = { navController.navigate(Routes.HISTORY) },
                onAddQuestion = { navController.navigate(Routes.ADD_QUESTION) }
            )
        }
        composable(Routes.ADD_QUESTION) {
            AddQuestionScreen(
                onSaved = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.HISTORY) {
            HistoryScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = Routes.GAME,
            arguments = listOf(
                navArgument(GameViewModel.ARG_CATEGORY) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(GameViewModel.ARG_DIFFICULTY) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            GameScreen(
                onFinished = { resultId ->
                    if (resultId == null) {
                        navController.popBackStack()
                    } else {
                        // popUpTo(HOME) saca la partida de la pila: "atrás" desde resultados va al inicio
                        navController.navigate(Routes.result(resultId)) {
                            popUpTo(Routes.HOME)
                        }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Routes.RESULT,
            arguments = listOf(
                navArgument(ResultViewModel.ARG_RESULT_ID) { type = NavType.StringType }
            )
        ) {
            ResultScreen(
                onPlayAgain = { category, difficulty ->
                    navController.navigate(Routes.game(category, difficulty)) {
                        popUpTo(Routes.HOME)
                    }
                },
                onGoHome = { navController.popBackStack(Routes.HOME, inclusive = false) }
            )
        }
    }
}