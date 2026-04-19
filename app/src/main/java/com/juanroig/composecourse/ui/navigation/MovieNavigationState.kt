package com.juanroig.composecourse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

@Composable
fun rememberMovieNavigationState(
    startDestination: TopLevelScreen = defaultRoute
): MovieNavigationState {
    val backStacks = topLevelDestinations.associateWith { destination ->
        rememberNavBackStack(destination)
    }

    return remember(backStacks, startDestination) {
        MovieNavigationState(
            startDestination = startDestination,
            backStacks = backStacks
        )
    }
}

@Stable
class MovieNavigationState(
    val startDestination: TopLevelScreen,
    private val backStacks: Map<TopLevelScreen, NavBackStack<NavKey>>
) {
    var currentTopLevelDestination: TopLevelScreen by mutableStateOf(startDestination)
        private set

    val currentBackStack: NavBackStack<NavKey>
        get() = backStacks.getValue(currentTopLevelDestination)

    val currentDestination: Screen
        get() = currentBackStack.last() as Screen

    fun navigateToTopLevel(destination: TopLevelScreen) {
        currentTopLevelDestination = destination
    }

    fun navigateTo(destination: Screen) {
        currentBackStack.add(destination)
    }

    fun popBackStack(): Boolean {
        return if (currentBackStack.size > 1) {
            currentBackStack.removeLast()
            true
        } else if (currentTopLevelDestination != startDestination) {
            currentTopLevelDestination = startDestination
            true
        } else {
            false
        }
    }
}
