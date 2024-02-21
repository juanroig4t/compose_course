package com.juanroig.composecourse.ui.screen.favScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanroig.composecourse.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    val repository: MovieRepository
): ViewModel() {

    var state by mutableStateOf(FavScreenState())
        private set

    init {
        repository.getMovieFavList().onEach {
            state = state.copy(listFavMovies = it)
        }.launchIn(viewModelScope)
    }

}