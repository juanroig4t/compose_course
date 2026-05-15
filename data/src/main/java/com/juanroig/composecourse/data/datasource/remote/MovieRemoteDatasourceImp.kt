package com.juanroig.composecourse.data.datasource.remote

import android.content.Context
import com.google.gson.Gson
import com.juanroig.composecourse.data.datasource.MovieRemoteDatasource
import com.juanroig.composecourse.data.datasource.remote.model.MovieDto
import com.juanroig.composecourse.domain.model.core.error.CustomFailure
import com.juanroig.composecourse.domain.model.core.error.NetworkFailure
import com.juanroig.composecourse.domain.model.core.result.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import okio.use
import retrofit2.HttpException
import java.io.BufferedReader
import kotlin.random.Random

class MovieRemoteDatasourceImp(
    private val retrofitMovieNetworkApi: RetrofitMovieNetworkApi
) : MovieRemoteDatasource {
    override suspend fun getPopularMovies(): Result<List<MovieDto>> {
        return try {
            val result = retrofitMovieNetworkApi.getPopularMovies("es-ES")
            Result.Success(result.results)
        } catch (e: HttpException) {
            Result.Error(NetworkFailure.ServerFailure(e.code().toString(), e.message()))
        } catch (e: Exception) {
            Result.Error(NetworkFailure.UnexpectedNetworkError(e.message ?: "Unexpected error"))
        }
    }
}

class FakeMovieRemoteDatasourceImp(
    private val retrofitMovieNetworkApi: RetrofitMovieNetworkApi,
    @param:ApplicationContext private val context: Context
) : MovieRemoteDatasource {

    companion object {
        private const val MOVIES_ASSET = "movieList.json"
    }
    override suspend fun getPopularMovies(): Result<List<MovieDto>> {
        context.assets.open(MOVIES_ASSET).use {
            delay(Random.nextLong(1000, 5000))
            val content = it.bufferedReader().use(BufferedReader::readText)
            val movieResponse = Gson().fromJson(content, MovieResponse::class.java)
            movieResponse.results?.let { movies ->
                return Result.Success(movies)
            } ?: return Result.Error(CustomFailure.NoResponse)
        }
    }
}

data class MovieResponse(
    val page: Int,
    val results: List<MovieDto>?,
    val total_pages: Int,
    val total_results: Int
)
