package com.juanroig.composecourse.data.repository

import com.juanroig.composecourse.data.datasource.MovieRemoteDatasource
import com.juanroig.composecourse.data.datasource.local.db.dao.MovieDao
import com.juanroig.composecourse.data.mapper.toDomain
import com.juanroig.composecourse.data.mapper.toEntity
import com.juanroig.composecourse.domain.model.core.error.CustomFailure
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

    override fun getTopTenMovies(): Flow<Result<List<Movie>>> {
        return movieDao.getTopTenMovies().map { movieList ->
            Result.Success(movieList.map { it.toDomain() })
        }
    }

    override fun getPopularMovies(): Flow<Result<List<Movie>>> {
        return movieDao.getPopularMovies().map { movieList ->
            Result.Success(movieList.map { it.toDomain() })
        }
    }

    override fun getMovieById(id: Int): Flow<Result<Movie>> {
        return movieDao.getMovieById(id).map { movie ->
            movie?.let { Result.Success(it.toDomain()) } ?: Result.Error(CustomFailure.NotFound)
        }
    }

    override suspend fun syncMovies(): Result<Unit> {
        var result: Result<Unit> = Result.Success(Unit)
        movieRemoteDatasource.getPopularMovies().apply {
            if (this is Result.Error) {
                result = Result.Error(this.failure)
            }
            if (this is Result.Success) {
                val favoriteMovieIds = movieDao.getFavoriteMovieIds().toSet()
                movieDao.insertMovieList(
                    this.data.map { movie ->
                        movie.copy(isFavorite = movie.id in favoriteMovieIds).toEntity()
                    }
                )
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
