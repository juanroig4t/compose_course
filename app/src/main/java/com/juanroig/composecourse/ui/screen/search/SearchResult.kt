package com.juanroig.composecourse.ui.screen.search

import com.juanroig.composecourse.domain.model.core.error.Failure
import com.juanroig.composecourse.domain.model.movie.Movie

sealed interface SearchResult {
    data class QueryChanged(val query: String) : SearchResult
    data object EmptyQuery : SearchResult
    data class Loading(val query: String) : SearchResult
    data class MoviesFound(val query: String, val movies: List<Movie>) : SearchResult
    data class SearchFailed(val query: String, val failure: Failure) : SearchResult
}
