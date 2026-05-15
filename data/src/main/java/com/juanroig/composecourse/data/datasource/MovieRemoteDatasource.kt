package com.juanroig.composecourse.data.datasource

import com.juanroig.composecourse.data.datasource.remote.model.MovieDto
import com.juanroig.composecourse.domain.model.core.result.Result

interface MovieRemoteDatasource {

    suspend fun getPopularMovies(): Result<List<MovieDto>>
}
