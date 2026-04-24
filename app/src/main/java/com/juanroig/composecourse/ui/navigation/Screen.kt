package com.juanroig.composecourse.ui.navigation

import androidx.navigation3.runtime.NavKey
import java.io.Serializable
import kotlinx.serialization.Serializable as KotlinSerializable

sealed interface Screen :
    NavKey,
    Serializable {
    val title: String
}

sealed interface TopLevelScreen : Screen

@KotlinSerializable
data object HomeScreen : TopLevelScreen {
    override val title: String = "Home"
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
    override val title: String = "Settings"
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
