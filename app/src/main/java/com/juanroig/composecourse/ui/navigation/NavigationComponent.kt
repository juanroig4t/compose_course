package com.juanroig.composecourse.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juanroig.composecourse.ui.MovieAppState
import com.juanroig.composecourse.ui.screen.DetailScreen
import com.juanroig.composecourse.ui.screen.dashboard.HomeScreen

@Composable
fun NavigationComponent(
    navController: NavHostController,
    appState: MovieAppState,
    showDrawerMenu: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            dashboardNavHost(appState, showDrawerMenu)
        }
        composable(Screen.SearchScreen.route) {
            appState.topBarState.value = appState.topBarState.value.copy(
                title = "Buscar",
                showNavigationIcon = true,
                menuIcon = Icons.Default.Menu,
                onNavigationIconClick = {
                    showDrawerMenu(true)
                }
            )
            Text(text = "Search Screen")
        }
        composable(Screen.FavScreen.route) {
            appState.topBarState.value = appState.topBarState.value.copy(
                title = "Favoritos",
                showNavigationIcon = true,
                menuIcon = Icons.Default.Menu,
                onNavigationIconClick = {
                    showDrawerMenu(true)
                }
            )
            Text(text = "Favorites Screen")
        }
        composable(Screen.SettingsScreen.route) {
            appState.topBarState.value = appState.topBarState.value.copy(
                title = "Settings",
                showNavigationIcon = true,
                menuIcon = Icons.Default.Menu,
                onNavigationIconClick = {
                    showDrawerMenu(true)
                }
            )
            Text(text = "Settings Screen")
        }
    }
}

@Composable
fun dashboardNavHost(
    appState: MovieAppState,
    showDrawerMenu: (Boolean) -> Unit
) {
    val dashboardController = rememberNavController()

    NavHost(navController = dashboardController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            appState.topBarState.value = appState.topBarState.value.copy(
                title = "Home",
                showNavigationIcon = true,
                menuIcon = Icons.Default.Menu,
                onNavigationIconClick = {
                    showDrawerMenu(true)
                }
            )
            HomeScreen(
                goToDetail = { movieId ->
                    dashboardController.navigate(Screen.DetailScreen.createRoute(movieId))
                }
            )
        }
        composable(
            route = Screen.DetailScreen.route
        ) {
            val movieId = it.arguments?.getString(NavArg.MovieIdArg.key) ?: "0"
            appState.topBarState.value = appState.topBarState.value.copy(
                title = "Detail $movieId",
                showNavigationIcon = true,
                menuIcon = Icons.Default.ArrowBack,
                onNavigationIconClick = {
                    dashboardController.popBackStack()
                }
            )

            DetailScreen(
                movieId.toInt(),
                goToHome = {
                    dashboardController.popBackStack()
                }
            )
        }
    }
}
