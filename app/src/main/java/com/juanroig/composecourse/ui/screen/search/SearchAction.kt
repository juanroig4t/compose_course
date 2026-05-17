package com.juanroig.composecourse.ui.screen.search

sealed interface SearchAction {
    data class UpdateQuery(val query: String) : SearchAction
    data class SearchMovies(val query: String) : SearchAction
    data class OpenMovieDetail(val movieId: Int) : SearchAction
}
