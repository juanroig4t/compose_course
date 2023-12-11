package com.juanroig.composecourse.domain.repository

import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie

interface MovieRepository {

    suspend fun getTopTenMovies(): Result<List<Movie>>
    suspend fun getPopularMovies(): Result<List<Movie>>

    suspend fun getMovieById(id: Int): Result<Movie>

    suspend fun syncMovies(): Result<Unit>

}