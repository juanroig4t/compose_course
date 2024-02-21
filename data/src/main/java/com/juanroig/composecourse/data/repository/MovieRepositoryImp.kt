package com.juanroig.composecourse.data.repository

import com.juanroig.composecourse.data.datasource.MovieRemoteDatasource
import com.juanroig.composecourse.data.datasource.local.db.dao.MovieDao
import com.juanroig.composecourse.data.mapper.toDomain
import com.juanroig.composecourse.data.mapper.toEntity
import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieDao: MovieDao,
    private val movieRemoteDatasource: MovieRemoteDatasource
) : MovieRepository {

    override suspend fun getTopTenMovies(): Flow<Result<List<Movie>>> {
        var listIsEmpty = false
        val localMovies = movieDao.getTopTenMovies().map { movieList ->
            listIsEmpty = movieList.isEmpty()
            Result.Success(movieList.map { it.toDomain() })
        }

        if (listIsEmpty) {
            syncMovies()
            return localMovies
        }

        return localMovies
    }

    override fun getPopularMovies(): Flow<Result<List<Movie>>> {
        return movieDao.getPopularMovies().map {
            if (it.isEmpty()) {
                syncMovies()
            }
            Result.Success(it.map { it.toDomain() })
        }
    }

    override fun getMovieById(id: Int): Flow<Result<Movie>> {
        return movieDao.getMovieById(id).map { Result.Success(it.toDomain()) }
    }

    override suspend fun syncMovies(): Result<Unit> {
        var result: Result<Unit> = Result.Success(Unit)
        movieRemoteDatasource.getPopularMovies().apply {
            if (this is Result.Error) {
                result = Result.Error(this.failure)
            }
            if (this is Result.Success) {
                movieDao.insertMovieList(this.data.map { it.toEntity() })
            }
        }

        return result
    }

    override suspend fun updateFavorite(movieId: Int, isFavorite: Boolean) {
        movieDao.updateFavorite(movieId, isFavorite)
    }

    override fun getMovieFavList(): Flow<List<Movie>> {
        return movieDao.getMovieFavList().map { it.map { it.toDomain() } }
    }
}
