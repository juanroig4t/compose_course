package com.juanroig.composecourse.data.repository

import com.juanroig.composecourse.data.datasource.MovieRemoteDatasource
import com.juanroig.composecourse.data.datasource.local.db.dao.MovieDao
import com.juanroig.composecourse.data.mapper.toDomain
import com.juanroig.composecourse.data.mapper.toEntity
import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.domain.repository.MovieRepository

class MovieRepositoryImp(
    private val movieDao: MovieDao,
    private val movieRemoteDatasource: MovieRemoteDatasource
) : MovieRepository {

    override suspend fun getTopTenMovies(): Result<List<Movie>> {

        val localMovies = movieDao.getTopTenMovies().map { it.toDomain() }

        if(localMovies.isEmpty()) {
            syncMovies()
            return Result.Success(movieDao.getTopTenMovies().map { it.toDomain() })
        }

        return Result.Success(localMovies)
    }

    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return Result.Success(movieDao.getPopularMovies().map { it.toDomain() })
    }

    override suspend fun getMovieById(id: Int): Result<Movie> {
        return Result.Success(movieDao.getMovieById(id).toDomain())
    }

    override suspend fun syncMovies(): Result<Unit> {

        var result: Result<Unit> = Result.Success(Unit)
        movieRemoteDatasource.getPopularMovies().apply {
            if(this is Result.Error) {
                result = Result.Error( this.failure)
            }
            if(this is Result.Success) {
                movieDao.insertMovieList(this.data.map { it.toEntity() })
            }
        }

        return result
    }

}