package com.juanroig.composecourse.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.juanroig.composecourse.ui.MovieAppState
import com.juanroig.composecourse.ui.navigateTo
import com.juanroig.composecourse.ui.popBackStack
import com.juanroig.composecourse.ui.screen.dashboard.HomeScreen as HomeScreenContent
import com.juanroig.composecourse.ui.screen.favScreen.FavScreen as FavScreenContent
import com.juanroig.composecourse.ui.screen.movieDetail.MovieDetailRoute
import com.juanroig.composecourse.ui.screen.movieDetail.MovieDetailViewModel

@Composable
fun NavigationComponent(
    appState: MovieAppState,
    innerPadding: PaddingValues,
    showDrawerMenu: (Boolean) -> Unit
) {
    NavDisplay(
        backStack = appState.navigationState.currentBackStack,
        onBack = { appState.popBackStack() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<HomeScreen> {
                appState.topBarState.value = appState.topBarState.value.copy(
                    title = HomeScreen.title,
                    showNavigationIcon = true,
                    menuIcon = Icons.Default.Menu,
                    onNavigationIconClick = { showDrawerMenu(true) }
                )
                HomeScreenContent(
                    contentPadding = innerPadding,
                    goToDetail = { movieId ->
                        appState.navigateTo(DetailScreen(movieId))
                    }
                )
            }
            entry<SearchScreen> {
                appState.topBarState.value = appState.topBarState.value.copy(
                    title = SearchScreen.title,
                    showNavigationIcon = true,
                    menuIcon = Icons.Default.Menu,
                    onNavigationIconClick = { showDrawerMenu(true) }
                )
                Text(text = "Search Screen")
            }
            entry<FavScreen> {
                appState.topBarState.value = appState.topBarState.value.copy(
                    title = FavScreen.title,
                    showNavigationIcon = true,
                    menuIcon = Icons.Default.Menu,
                    onNavigationIconClick = { showDrawerMenu(true) }
                )
                FavScreenContent(
                    contentPadding = innerPadding,
                    goToDetailMovie = { movieId ->
                        appState.navigateTo(DetailScreen(movieId))
                    }
                )
            }
            entry<SettingsScreen> {
                appState.topBarState.value = appState.topBarState.value.copy(
                    title = SettingsScreen.title,
                    showNavigationIcon = true,
                    menuIcon = Icons.Default.Menu,
                    onNavigationIconClick = { showDrawerMenu(true) }
                )
                Text(text = "Settings Screen")
            }
            entry<DetailScreen> { key ->
                appState.topBarState.value = appState.topBarState.value.copy(
                    title = key.title,
                    showNavigationIcon = true,
                    menuIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    onNavigationIconClick = { appState.popBackStack() }
                )
                MovieDetailRoute(
                    contentPadding = innerPadding,
                    viewModel = hiltViewModel<MovieDetailViewModel, MovieDetailViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create(key)
                        }
                    )
                )
            }
        }
    )
}
