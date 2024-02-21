package com.juanroig.composecourse.ui.screen.dashboard

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
import com.juanroig.composecourse.domain.usecase.ObtainTopTenMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val obtainTopTenMovies: ObtainTopTenMoviesUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val deleteMovieToFavoriteUseCase: DeleteMovieToFavoriteUseCase,
    private val movieRepository: MovieRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    val flowLifecycleState: StateFlow<Result<List<Movie>>> = movieRepository.getPopularMovies()
        .stateIn(
            scope = viewModelScope,
            initialValue = Result.Success(emptyList()),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    init {
        viewModelScope.launch {
            obtainTopTenMovies().onEach { result ->
                state = when (result) {
                    is Result.Error -> {
                        state.copy(error = result.failure)
                    }

                    is Result.Success -> {
                        state.copy(topTenMovies = result.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
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
