package com.juanroig.composecourse.ui.component

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.dropUnlessResumed
import com.juanroig.composecourse.ui.MovieAppState
import com.juanroig.composecourse.ui.currentTopLevelDestination
import com.juanroig.composecourse.ui.navigateToTopLevel
import com.juanroig.composecourse.ui.navigation.topLevelNavigationItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieBottomAppBar(
    appState: MovieAppState
) {
    NavigationBar() {
        topLevelNavigationItems.forEach() { item ->
            val isSelected = item.destination == appState.currentTopLevelDestination
            NavigationBarItem(
                selected = isSelected,
                onClick = dropUnlessResumed {
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
