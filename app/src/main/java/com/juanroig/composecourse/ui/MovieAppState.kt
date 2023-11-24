package com.juanroig.composecourse.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.juanroig.composecourse.ui.component.topBar.TopBarState
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberMovieAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    topBarState: MutableState<TopBarState> = remember { mutableStateOf(TopBarState()) },
): MovieAppState = remember(navController) {
    MovieAppState(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarState = topBarState,
    )
}

data class MovieAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val topBarState: MutableState<TopBarState>,
)