package com.juanroig.composecourse.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.juanroig.composecourse.ui.MovieAppState
import com.juanroig.composecourse.ui.currentDestination
import com.juanroig.composecourse.ui.navigateTo
import com.juanroig.composecourse.ui.popBackStack
import com.juanroig.composecourse.ui.component.topBar.TopBarState
import com.juanroig.composecourse.ui.screen.movieDetail.MovieDetailRoute
import com.juanroig.composecourse.ui.screen.movieDetail.MovieDetailViewModel
import com.juanroig.composecourse.ui.screen.dashboard.HomeScreen as HomeScreenContent
import com.juanroig.composecourse.ui.screen.favScreen.FavScreen as FavScreenContent

@Composable
fun NavigationComponent(
    appState: MovieAppState,
    showDrawerMenu: (Boolean) -> Unit
) {
    val openDrawer = dropUnlessResumed { showDrawerMenu(true) }
    val navigateBack = dropUnlessResumed { appState.popBackStack() }
    val currentDestination = appState.currentDestination

    SideEffect {
        appState.topBarState.value = currentDestination.toTopBarState(
            openDrawer = openDrawer,
            navigateBack = navigateBack
        )
    }

    NavDisplay(
        backStack = appState.navigationState.currentBackStack,
        onBack = { appState.popBackStack() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<HomeScreen> {
                HomeScreenContent(
                    goToDetail = { movieId ->
                        appState.navigateTo(DetailScreen(movieId))
                    }
                )
            }
            entry<SearchScreen> {
                Text(text = "Search Screen")
            }
            entry<FavScreen> {
                FavScreenContent(
                    goToDetailMovie = { movieId ->
                        appState.navigateTo(DetailScreen(movieId))
                    }
                )
            }
            entry<SettingsScreen> {
                Text(text = "Settings Screen")
            }
            entry<DetailScreen> { key ->
                MovieDetailRoute(
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

private fun Screen.toTopBarState(
    openDrawer: () -> Unit,
    navigateBack: () -> Unit
): TopBarState =
    TopBarState(
        title = title,
        showNavigationIcon = true,
        menuIcon = if (this is TopLevelScreen) {
            Icons.Default.Menu
        } else {
            Icons.AutoMirrored.Filled.ArrowBack
        },
        onNavigationIconClick = if (this is TopLevelScreen) openDrawer else navigateBack
    )
