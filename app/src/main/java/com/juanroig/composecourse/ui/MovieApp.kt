package com.juanroig.composecourse.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.juanroig.composecourse.ui.component.MovieBottomAppBar
import com.juanroig.composecourse.ui.component.MovieDrawer
import com.juanroig.composecourse.ui.component.topBar.MovieTopAppBar
import com.juanroig.composecourse.ui.navigation.NavigationComponent
import kotlinx.coroutines.launch

@Composable
fun MovieApp(
    appState: MovieAppState = rememberMovieAppState()
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MovieDrawer()
        })
    {
        Scaffold(
            topBar = {
                MovieTopAppBar(
                    topBarState = appState.topBarState.value
                )
            },
            bottomBar = {
                MovieBottomAppBar(
                    appState.navController
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
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




