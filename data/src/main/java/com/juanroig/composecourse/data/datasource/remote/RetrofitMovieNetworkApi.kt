package com.juanroig.composecourse.data.datasource.remote

import com.juanroig.composecourse.data.datasource.remote.model.MovieDto
import com.juanroig.composecourse.data.datasource.remote.model.NetworkPagingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitMovieNetworkApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String
    ): NetworkPagingResponse<List<MovieDto>>

}