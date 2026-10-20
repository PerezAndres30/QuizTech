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
import com.andre.techquiz.presentation.viewmodel.GameViewModel

object Routes {
    const val HOME = "home"
    const val GAME = "game?category={category}&difficulty={difficulty}"

    fun game(category: Category?, difficulty: Difficulty?): String =
        "game?category=${category?.name.orEmpty()}&difficulty=${difficulty?.name.orEmpty()}"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onStartGame = { category, difficulty ->
                    navController.navigate(Routes.game(category, difficulty))
                }
            )
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
                // Temporal: al terminar vuelve al inicio. Con ResultScreen navegará a resultados.
                onFinished = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}