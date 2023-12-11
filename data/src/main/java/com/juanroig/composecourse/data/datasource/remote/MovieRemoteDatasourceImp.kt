package com.juanroig.composecourse.data.datasource.remote

import com.juanroig.composecourse.data.datasource.MovieRemoteDatasource
import com.juanroig.composecourse.data.mapper.toDomain
import com.juanroig.composecourse.domain.model.core.error.CustomFailure
import com.juanroig.composecourse.domain.model.core.error.NetworkFailure
import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import retrofit2.HttpException

class MovieRemoteDatasourceImp(
    private val retrofitMovieNetworkApi: RetrofitMovieNetworkApi
): MovieRemoteDatasource {
    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return try {
            val result = retrofitMovieNetworkApi.getPopularMovies("es-ES")
            result.results.let {movieList ->
                Result.Success(movieList.map { it.toDomain() })
            }
        } catch (e: HttpException) {
            Result.Error(NetworkFailure.ServerFailure(e.code().toString(), e.message()))
        } catch (e: Exception) {
            Result.Error(NetworkFailure.UnexpectedNetworkError(e.message ?: "Unexpected error"))
        }

    }

}