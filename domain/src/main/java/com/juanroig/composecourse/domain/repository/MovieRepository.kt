package com.juanroig.composecourse.domain.repository

import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getTopTenMovies(): Flow<Result<List<Movie>>>
    fun getPopularMovies(): Flow<Result<List<Movie>>>

    fun getMovieById(id: Int): Flow<Result<Movie>>

    suspend fun syncMovies(): Result<Unit>

    suspend fun updateFavorite(movieId: Int, isFavorite: Boolean)
    fun getMovieFavList(): Flow<List<Movie>>
}
