package com.juanroig.composecourse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.serialization.NavKeySerializer
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer

@Composable
fun rememberMovieNavigationState(startDestination: TopLevelScreen = defaultRoute): MovieNavigationState {
    val currentTopLevelDestination = rememberSerializable(
        serializer = MutableStateSerializer(NavKeySerializer())
    ) {
        androidx.compose.runtime.mutableStateOf(startDestination)
    }
    val backStacks =
        topLevelDestinations.associateWith { destination ->
            rememberNavBackStack(destination)
        }

    return remember(backStacks, startDestination) {
        MovieNavigationState(
            startDestination = startDestination,
            currentTopLevelDestination = currentTopLevelDestination,
            backStacks = backStacks
        )
    }
}

@Stable
class MovieNavigationState(
    val startDestination: TopLevelScreen,
    currentTopLevelDestination: MutableState<TopLevelScreen>,
    private val backStacks: Map<TopLevelScreen, NavBackStack<NavKey>>
) {
    var currentTopLevelDestination: TopLevelScreen by currentTopLevelDestination
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

    fun popBackStack(): Boolean =
        if (currentBackStack.size > 1) {
            currentBackStack.removeAt(currentBackStack.lastIndex)
            true
        } else if (currentTopLevelDestination != startDestination) {
            currentTopLevelDestination = startDestination
            true
        } else {
            false
        }
}
