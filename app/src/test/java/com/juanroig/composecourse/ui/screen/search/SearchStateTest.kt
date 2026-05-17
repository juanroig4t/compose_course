package com.juanroig.composecourse.ui.screen.search

import com.juanroig.composecourse.domain.model.movie.Movie
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchStateTest {

    @Test
    fun `empty query is initial state`() {
        val state = SearchState()

        assertTrue(state.isInitial)
        assertFalse(state.isEmptyResult)
    }

    @Test
    fun `query without movies is empty result`() {
        val state = SearchState(query = "matrix")

        assertFalse(state.isInitial)
        assertTrue(state.isEmptyResult)
    }

    @Test
    fun `query with movies is not empty result`() {
        val state = SearchState(
            query = "matrix",
            movies = listOf(movie())
        )

        assertFalse(state.isInitial)
        assertFalse(state.isEmptyResult)
    }

    private fun movie() = Movie(
        adult = false,
        backdropPath = "",
        genreIds = emptyList(),
        id = 1,
        originalLanguage = "en",
        originalTitle = "The Matrix",
        overview = "Overview",
        popularity = 1.0,
        posterPath = "",
        releaseDate = "1999-03-31",
        title = "The Matrix",
        video = false,
        voteAverage = 8.7,
        voteCount = 10,
        isFavorite = false
    )
}
