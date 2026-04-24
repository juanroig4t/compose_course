package com.juanroig.composecourse.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.juanroig.composecourse.ui.MovieAppState
import com.juanroig.composecourse.ui.currentTopLevelDestination
import com.juanroig.composecourse.ui.navigateToTopLevel
import com.juanroig.composecourse.ui.navigation.topLevelNavigationItems

@Composable
fun MovieDrawer(
    appState: MovieAppState,
    toggleDrawerMenu: () -> Unit
) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(16.dp))
        topLevelNavigationItems.forEachIndexed() { _, item ->
            val isSelected = item.destination == appState.currentTopLevelDestination
            NavigationDrawerItem(
                label = { Text(text = item.title) },
                selected = isSelected,
                onClick = dropUnlessResumed {
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
