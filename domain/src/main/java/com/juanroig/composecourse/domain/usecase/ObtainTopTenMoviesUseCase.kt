package com.juanroig.composecourse.domain.usecase

import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.domain.repository.MovieRepository
import javax.inject.Inject

class ObtainTopTenMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Result<List<Movie>> {
        return movieRepository.getTopTenMovies()
    }
}