package com.juanroig.composecourse.ui.screen.movieDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.domain.repository.MovieRepository
import com.juanroig.composecourse.domain.usecase.AddMovieToFavoriteUseCase
import com.juanroig.composecourse.domain.usecase.DeleteMovieToFavoriteUseCase
import com.juanroig.composecourse.ui.navigation.DetailScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MovieDetailViewModel.Factory::class)
class MovieDetailViewModel @AssistedInject constructor(
    private val movieRepository: MovieRepository,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val deleteMovieToFavoriteUseCase: DeleteMovieToFavoriteUseCase,
    @Assisted private val detailScreen: DetailScreen
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(detailScreen: DetailScreen): MovieDetailViewModel
    }

    var state by mutableStateOf(DetailState())
        private set

    init {
        movieRepository.getMovieById(detailScreen.movieId).onEach { movieResult ->
            state = when (movieResult) {
                is Result.Error -> {
                    state.copy(error = movieResult.failure)
                }

                is Result.Success -> {
                    state.copy(movie = movieResult.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onFavoriteClick(movie: Movie) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                deleteMovieToFavoriteUseCase(movie.id)
            } else {
                addMovieToFavoriteUseCase(movie.id)
            }
        }
    }
}
