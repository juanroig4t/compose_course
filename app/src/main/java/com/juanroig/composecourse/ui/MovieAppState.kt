package com.juanroig.composecourse.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.juanroig.composecourse.ui.component.topBar.TopBarState
import com.juanroig.composecourse.ui.navigation.MovieNavigationState
import com.juanroig.composecourse.ui.navigation.Screen
import com.juanroig.composecourse.ui.navigation.TopLevelScreen
import com.juanroig.composecourse.ui.navigation.rememberMovieNavigationState
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberMovieAppState(
    navigationState: MovieNavigationState = rememberMovieNavigationState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    topBarState: MutableState<TopBarState> = remember { mutableStateOf(TopBarState()) }
): MovieAppState =
    remember(navigationState) {
        MovieAppState(
            navigationState = navigationState,
            coroutineScope = coroutineScope,
            topBarState = topBarState
        )
    }

data class MovieAppState(
    val navigationState: MovieNavigationState,
    val coroutineScope: CoroutineScope,
    val topBarState: MutableState<TopBarState>
)

fun MovieAppState.navigateToTopLevel(destination: TopLevelScreen) {
    navigationState.navigateToTopLevel(destination)
}

fun MovieAppState.navigateTo(destination: Screen) {
    navigationState.navigateTo(destination)
}

fun MovieAppState.popBackStack(): Boolean = navigationState.popBackStack()

val MovieAppState.currentTopLevelDestination: TopLevelScreen
    get() = navigationState.currentTopLevelDestination

val MovieAppState.currentDestination: Screen
    get() = navigationState.currentDestination

val MovieAppState.currentBackStack
    get() = navigationState.currentBackStack
