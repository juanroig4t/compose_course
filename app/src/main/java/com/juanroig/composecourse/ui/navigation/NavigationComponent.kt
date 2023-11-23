package com.juanroig.composecourse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juanroig.composecourse.ui.screen.DetailScreen
import com.juanroig.composecourse.ui.screen.HomeScreen

@Composable
fun NavigationComponent(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                goToDetail = { movieId ->
                    navController.navigate(Screen.DetailScreen.createRoute(movieId))
                }
            )
        }
        composable(
            route = Screen.DetailScreen.route
        ) {
            val movieId = it.arguments?.getString(NavArg.MovieIdArg.key) ?: "0"

            DetailScreen(
                movieId.toInt(),
                goToHome = {
                    navController.popBackStack()
                }
            )
        }
    }
}
