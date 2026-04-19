package com.juanroig.composecourse.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanroig.composecourse.ui.MovieAppState
import com.juanroig.composecourse.ui.currentTopLevelDestination
import com.juanroig.composecourse.ui.navigateToTopLevel
import com.juanroig.composecourse.ui.navigation.FavScreen
import com.juanroig.composecourse.ui.navigation.HomeScreen
import com.juanroig.composecourse.ui.navigation.SearchScreen
import com.juanroig.composecourse.ui.navigation.SettingsScreen

@Composable
fun MovieDrawer(
    appState: MovieAppState,
    toggleDrawerMenu: () -> Unit
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

    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(16.dp))
        items.forEachIndexed() { _, item ->
            val isSelected = item.destination == appState.currentTopLevelDestination
            NavigationDrawerItem(
                label = { Text(text = item.title) },
                selected = isSelected,
                onClick = {
                    toggleDrawerMenu()
                    appState.navigateToTopLevel(item.destination)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                badge = {
                    item.badgedCount?.let {
                        Text(text = it.toString())
                    }
                },
                modifier = Modifier
                    .padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}
