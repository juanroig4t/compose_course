package com.juanroig.composecourse.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberMovieAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MovieAppState = remember(navController) {
    MovieAppState(
        navController = navController,
        coroutineScope = coroutineScope
    )
}

data class MovieAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
)