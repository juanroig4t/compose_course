package com.juanroig.composecourse.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<SearchEffect>()
    val effect: SharedFlow<SearchEffect> = _effect.asSharedFlow()

    init {
        searchQuery
            .debounce(SEARCH_DEBOUNCE_MS)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                val trimmedQuery = query.trim()
                if (trimmedQuery.isBlank()) {
                    flowOf(SearchResult.EmptyQuery)
                } else {
                    movieRepository
                        .searchMovies(trimmedQuery)
                        .map { result ->
                            when (result) {
                                is Result.Error -> SearchResult.SearchFailed(
                                    query = query,
                                    failure = result.failure
                                )

                                is Result.Success -> SearchResult.MoviesFound(
                                    query = query,
                                    movies = result.data
                                )
                            }
                        }
                        .onStart {
                            emit(SearchResult.Loading(query))
                        }
                }
            }
            .onEach { result ->
                _uiState.update { state -> reduce(state, result) }
            }
            .launchIn(viewModelScope)
    }

    fun accept(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.MovieClicked -> dispatch(SearchAction.OpenMovieDetail(intent.movieId))
            is SearchIntent.QueryChanged -> dispatch(SearchAction.UpdateQuery(intent.query))
        }
    }

    private fun dispatch(action: SearchAction) {
        when (action) {
            is SearchAction.OpenMovieDetail -> {
                viewModelScope.launch {
                    _effect.emit(SearchEffect.NavigateToDetail(action.movieId))
                }
            }

            is SearchAction.SearchMovies -> {
                searchQuery.value = action.query
            }

            is SearchAction.UpdateQuery -> {
                _uiState.update { state ->
                    reduce(state, SearchResult.QueryChanged(action.query))
                }
                dispatch(SearchAction.SearchMovies(action.query))
            }
        }
    }

    private fun reduce(state: SearchState, result: SearchResult): SearchState =
        when (result) {
            SearchResult.EmptyQuery -> SearchState()
            is SearchResult.Loading -> state.copy(
                query = result.query,
                isLoading = true,
                movies = emptyList(),
                errorMessage = null
            )

            is SearchResult.MoviesFound -> state.copy(
                query = result.query,
                isLoading = false,
                movies = result.movies,
                errorMessage = null
            )

            is SearchResult.QueryChanged -> state.copy(
                query = result.query,
                errorMessage = null
            )

            is SearchResult.SearchFailed -> state.copy(
                query = result.query,
                isLoading = false,
                movies = emptyList(),
                errorMessage = result.failure.data?.toString() ?: "No se pudo buscar peliculas."
            )
        }

    private companion object {
        const val SEARCH_DEBOUNCE_MS = 300L
    }
}
