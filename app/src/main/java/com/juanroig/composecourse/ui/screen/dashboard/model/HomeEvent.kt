package com.juanroig.composecourse.ui.screen.dashboard.model

import com.juanroig.composecourse.common.UiEvent
import com.juanroig.composecourse.domain.model.movie.Movie

sealed class HomeEvent : UiEvent {
    class OnFavoriteClick(val movie: Movie) : HomeEvent()
}
