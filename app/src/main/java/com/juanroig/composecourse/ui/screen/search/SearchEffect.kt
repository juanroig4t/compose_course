package com.juanroig.composecourse.ui.screen.search

sealed interface SearchEffect {
    data class NavigateToDetail(val movieId: Int) : SearchEffect
}
