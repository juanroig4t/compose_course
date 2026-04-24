package com.juanroig.composecourse.ui.screen.dashboard.model

import com.juanroig.composecourse.common.UiEffect

sealed class HomeEffect : UiEffect {
    data class NavigateToDetail(
        val movieId: Int
    ) : HomeEffect()
}
