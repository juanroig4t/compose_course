package com.juanroig.composecourse.ui.screen.search

import androidx.compose.runtime.Immutable
import com.juanroig.composecourse.domain.model.movie.Movie

@Immutable
data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val errorMessage: String? = null
) {
    val isInitial: Boolean
        get() = query.isBlank() && !isLoading && errorMessage == null

    val isEmptyResult: Boolean
        get() = query.isNotBlank() && !isLoading && movies.isEmpty() && errorMessage == null
}
