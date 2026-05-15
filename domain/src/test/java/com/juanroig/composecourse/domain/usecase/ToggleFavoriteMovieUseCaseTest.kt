package com.juanroig.composecourse.domain.usecase

import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ToggleFavoriteMovieUseCaseTest {
    @Test
    fun `marks movie as favorite when it is not favorite`() = runBlocking {
        val repository = FakeMovieRepository()
        val useCase = ToggleFavoriteMovieUseCase(repository)

        useCase(movie(isFavorite = false))

        assertEquals(1, repository.updatedMovieId)
        assertEquals(true, repository.updatedIsFavorite)
    }

    @Test
    fun `removes movie from favorites when it is already favorite`() = runBlocking {
        val repository = FakeMovieRepository()
        val useCase = ToggleFavoriteMovieUseCase(repository)

        useCase(movie(isFavorite = true))

        assertEquals(1, repository.updatedMovieId)
        assertEquals(false, repository.updatedIsFavorite)
    }

    private fun movie(isFavorite: Boolean) = Movie(
        adult = false,
        backdropPath = "",
        genreIds = emptyList(),
        id = 1,
        originalLanguage = "es",
        originalTitle = "Movie",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "",
        releaseDate = "2026-05-16",
        title = "Movie",
        video = false,
        voteAverage = 7.0,
        voteCount = 10,
        isFavorite = isFavorite
    )

    private class FakeMovieRepository : MovieRepository {
        var updatedMovieId: Int? = null
        var updatedIsFavorite: Boolean? = null

        override fun getTopTenMovies(): Flow<Result<List<Movie>>> = emptyFlow()

        override fun getPopularMovies(): Flow<Result<List<Movie>>> = emptyFlow()

        override fun getMovieById(id: Int): Flow<Result<Movie>> = emptyFlow()

        override suspend fun syncMovies(): Result<Unit> = Result.Success(Unit)

        override suspend fun updateFavorite(movieId: Int, isFavorite: Boolean) {
            updatedMovieId = movieId
            updatedIsFavorite = isFavorite
        }

        override fun getMovieFavList(): Flow<List<Movie>> = emptyFlow()
    }
}
