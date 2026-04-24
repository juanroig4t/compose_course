package com.juanroig.composecourse.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable as KotlinSerializable

sealed interface Screen : NavKey {
    val title: String
}

sealed interface TopLevelScreen : Screen

@KotlinSerializable
data object HomeScreen : TopLevelScreen {
    override val title: String = "Inicio"
}

@KotlinSerializable
data object SearchScreen : TopLevelScreen {
    override val title: String = "Buscar"
}

@KotlinSerializable
data object FavScreen : TopLevelScreen {
    override val title: String = "Favoritos"
}

@KotlinSerializable
data object SettingsScreen : TopLevelScreen {
    override val title: String = "Ajustes"
}

@KotlinSerializable
data class DetailScreen(
    val movieId: Int
) : Screen {
    override val title: String = "Detalles"
}

val defaultRoute: TopLevelScreen = HomeScreen

val topLevelDestinations =
    listOf(
        HomeScreen,
        SearchScreen,
        FavScreen,
        SettingsScreen
    )
