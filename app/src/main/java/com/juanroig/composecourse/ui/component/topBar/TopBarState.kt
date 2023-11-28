package com.juanroig.composecourse.ui.component.topBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class TopBarState(
    var hide: Boolean = true,
    var title: String = "",
    var isDark: Boolean = true,
    var showNavigationIcon: Boolean = false,
    var actions: (@Composable RowScope.() -> Unit) = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info icon"
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Account icon"
            )
        }
    },
    var menuIcon: ImageVector = Icons.Filled.ArrowBack,
    var onNavigationIconClick: (() -> Unit) = {}
)
