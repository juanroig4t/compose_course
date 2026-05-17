package com.juanroig.composecourse.ui.screen.search

sealed interface SearchIntent {
    data class QueryChanged(val query: String) : SearchIntent
    data class MovieClicked(val movieId: Int) : SearchIntent
}
