package com.juanroig.composecourse.domain.usecase

import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.domain.repository.MovieRepository
import javax.inject.Inject

class ObtainTopTenMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun invoke(): Result<List<Movie>> {
        return movieRepository.getTopTenMovies()
    }
}