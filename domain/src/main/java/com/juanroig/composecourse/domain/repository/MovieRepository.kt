package com.juanroig.composecourse.domain.repository

import com.juanroig.composecourse.domain.model.movie.Movie

interface MovieRepository {

    suspend fun getTopTenMovies(): List<Movie>
    suspend fun getPopularMovies(): List<Movie>

    suspend fun getMovieById(id: Int): Movie

}