package com.juanroig.composecourse.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanroig.composecourse.ui.MovieAppState
import com.juanroig.composecourse.ui.currentTopLevelDestination
import com.juanroig.composecourse.ui.navigateToTopLevel
import com.juanroig.composecourse.ui.navigation.FavScreen
import com.juanroig.composecourse.ui.navigation.HomeScreen
import com.juanroig.composecourse.ui.navigation.SearchScreen
import com.juanroig.composecourse.ui.navigation.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieNavRail(
    appState: MovieAppState
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Inicio",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            destination = HomeScreen
        ),
        BottomNavigationItem(
            title = "Buscar",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            destination = SearchScreen
        ),
        BottomNavigationItem(
            title = "Favoritos",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite,
            destination = FavScreen
        ),
        BottomNavigationItem(
            title = "Ajustes",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            badgedCount = 10,
            destination = SettingsScreen
        )
    )

    NavigationRail(
        modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
            .offset(x = (-1).dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom)
        ) {
            items.forEachIndexed() { _, item ->
                val isSelected = item.destination == appState.currentTopLevelDestination
                NavigationRailItem(
                    selected = isSelected,
                    onClick = {
                        appState.navigateToTopLevel(item.destination)
                    },
                    label = { Text(text = item.title) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgedCount != null) {
                                    Badge {
                                        Text(text = item.badgedCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    }
                )
            }
        }
    }
}
