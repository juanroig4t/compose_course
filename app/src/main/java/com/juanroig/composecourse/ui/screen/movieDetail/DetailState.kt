package com.juanroig.composecourse.ui.screen.movieDetail

import com.juanroig.composecourse.domain.model.core.error.Failure
import com.juanroig.composecourse.domain.model.movie.Movie

data class DetailState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: Failure? = null
)
