package com.juanroig.composecourse.ui.screen.dashboard

import com.juanroig.composecourse.domain.model.core.error.Failure
import com.juanroig.composecourse.domain.model.movie.Movie

data class HomeState(
    val isLoading: Boolean = false,
    val topTenMovies: List<Movie> = emptyList(),
    val error: Failure? = null
)
