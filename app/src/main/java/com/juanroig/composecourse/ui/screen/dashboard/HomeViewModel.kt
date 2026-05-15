package com.juanroig.composecourse.ui.screen.dashboard

import androidx.lifecycle.viewModelScope
import com.juanroig.composecourse.common.BaseViewModel
import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.domain.repository.MovieRepository
import com.juanroig.composecourse.domain.usecase.ObserveTopTenMoviesUseCase
import com.juanroig.composecourse.domain.usecase.ToggleFavoriteMovieUseCase
import com.juanroig.composecourse.ui.screen.dashboard.model.HomeEffect
import com.juanroig.composecourse.ui.screen.dashboard.model.HomeEvent
import com.juanroig.composecourse.ui.screen.dashboard.model.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val observeTopTenMovies: ObserveTopTenMoviesUseCase,
        private val toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase,
        private val movieRepository: MovieRepository
    ) : BaseViewModel<HomeEvent, HomeState, HomeEffect>() {
        init {
            // Home es el ejemplo principal de UDF: el ViewModel observa datos,
            // reduce resultados a HomeState y la UI solo renderiza ese estado.
            collectTopTenMovies()
            observePopularMovies()
            syncMovies()
        }

        private fun collectTopTenMovies() {
            observeTopTenMovies()
                .onEach { result ->
                    when (result) {
                        is Result.Error -> {
                            setState { copy(isLoading = false, error = result.failure) }
                        }

                        is Result.Success -> {
                            setState { copy(topTenMovies = result.data, error = null) }
                        }
                    }
                }.launchIn(viewModelScope)
        }

        private fun observePopularMovies() {
            movieRepository
                .getPopularMovies()
                .onEach { result ->
                    when (result) {
                        is Result.Error -> {
                            setState { copy(isLoading = false, error = result.failure) }
                        }

                        is Result.Success -> {
                            setState {
                                copy(
                                    isLoading = if (result.data.isNotEmpty()) false else isLoading,
                                    popularMovies = result.data,
                                    error = null
                                )
                            }
                        }
                    }
                }.launchIn(viewModelScope)
        }

        fun syncMovies() {
            viewModelScope.launch {
                setState { copy(isLoading = true, error = null) }
                when (val result = movieRepository.syncMovies()) {
                    is Result.Error -> {
                        setState { copy(isLoading = false, error = result.failure) }
                    }

                    is Result.Success -> {
                        setState { copy(isLoading = false, error = null) }
                    }
                }
            }
        }

        private fun onFavoriteClick(movie: Movie) {
            viewModelScope.launch {
                toggleFavoriteMovieUseCase(movie)
                if (!movie.isFavorite) {
                    setEffect { HomeEffect.NavigateToDetail(movie.id) }
                }
            }
        }

        override fun createInitialState(): HomeState = HomeState()

        override fun handleEvent(event: HomeEvent) {
            when (event) {
                is HomeEvent.OnFavoriteClick -> onFavoriteClick(event.movie)
            }
        }
    }
