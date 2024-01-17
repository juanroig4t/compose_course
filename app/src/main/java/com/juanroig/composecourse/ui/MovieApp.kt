package com.juanroig.composecourse.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.juanroig.composecourse.ui.component.MovieBottomAppBar
import com.juanroig.composecourse.ui.component.MovieDrawer
import com.juanroig.composecourse.ui.component.MovieNavRail
import com.juanroig.composecourse.ui.component.topBar.MovieTopAppBar
import com.juanroig.composecourse.ui.navigation.NavigationComponent
import kotlinx.coroutines.launch

@Composable
fun MovieApp(
    windowSizeClass: WindowSizeClass,
    appState: MovieAppState = rememberMovieAppState()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MovieDrawer(
                appState.navController,
                toggleDrawerMenu = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                MovieTopAppBar(
                    topBarState = appState.topBarState.value
                )
            },
            bottomBar = {
                if (shouldShowBottomBar(windowSizeClass)) {
                    MovieBottomAppBar(
                        appState.navController
                    )
                }
            }
        ) { padding ->

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (!shouldShowBottomBar(windowSizeClass)) {
                    MovieNavRail(appState.navController)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NavigationComponent(
                        navController = appState.navController,
                        appState = appState,
                        showDrawerMenu = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun shouldShowBottomBar(windowSizeClass: WindowSizeClass): Boolean {
    return windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
        windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
}
