package com.juanroig.composecourse.ui.screen.favScreen

import com.juanroig.composecourse.domain.model.core.error.Failure
import com.juanroig.composecourse.domain.model.movie.Movie

data class FavScreenState (
    val isLoading: Boolean = false,
    val listFavMovies: List<Movie> = emptyList(),
    val error: Failure? = null
)