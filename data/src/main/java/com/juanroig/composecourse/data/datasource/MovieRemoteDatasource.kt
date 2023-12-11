package com.juanroig.composecourse.data.datasource

import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie

interface MovieRemoteDatasource {

    suspend fun getPopularMovies(): Result<List<Movie>>

}