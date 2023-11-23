package com.juanroig.composecourse.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val basicRoute: String, private val navArgs: List<NavArg> = emptyList()) {
    object HomeScreen : Screen("home_screen")
    object DetailScreen : Screen("detail_screen", listOf(NavArg.MovieIdArg)) {
        fun createRoute(movieId: Int): String = "$basicRoute/$movieId"
    }

    object DetailOptionalArgumentScreen : Screen("detail_screen", listOf(NavArg.OptionalArg)) {
        fun createRoute(optionalArg: String?): String = "$basicRoute?${NavArg.OptionalArg.key}=$optionalArg"
    }

    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(basicRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args: List<NamedNavArgument>
        get() = navArgs.map {
            navArgument(it.key) { type = it.navType }
        }
}

enum class NavArg(val key: String, val navType: NavType<*>) {
    MovieIdArg("movieId", NavType.IntType),
    OptionalArg("optionalString", NavType.IntType)
}

val defaultRoute: String
    get() = Screen.HomeScreen.route
