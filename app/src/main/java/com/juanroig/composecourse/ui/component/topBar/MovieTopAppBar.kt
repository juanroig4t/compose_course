package com.juanroig.composecourse.ui.component.topBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    topBarState: TopBarState,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

//    CenterAlignedTopAppBar(
    TopAppBar(
//    MediumTopAppBar(
//    LargeTopAppBar(
        navigationIcon = {
            if (topBarState.showNavigationIcon) {
                IconButton(onClick = topBarState.onNavigationIconClick) {
                    Icon(
                        imageVector = topBarState.menuIcon,
                        contentDescription = "Navigation icon"
                    )
                }
            }
        },
        title = {
            Text(text = topBarState.title)
        },
        actions = topBarState.actions,
        scrollBehavior = scrollBehavior,
//        colors = topAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.primary,
//        )
    )
}