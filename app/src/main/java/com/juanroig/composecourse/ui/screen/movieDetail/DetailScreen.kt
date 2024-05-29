package com.juanroig.composecourse.ui.screen.movieDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.ui.component.FavIconButton
import com.juanroig.composecourse.ui.extension.toYear
import com.juanroig.composecourse.ui.theme.ComposeCourseTheme

@Composable
fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state

    DetailScreen(state, viewModel::onFavoriteClick)
}

@Composable
private fun DetailScreen(
    state: DetailState,
    onFavoriteClick: (Movie) -> Unit
) {
    state.movie?.let { movie ->
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = "https://image.tmdb.org/t/p/w500${movie.backdropPath}",
            contentDescription = "backdrop Path",
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = movie.title,
            style = MaterialTheme.typography.titleLarge
        )
    }

    if (state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Cargando")
        }
    }
}

@Preview(device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    var movie = Movie(
        adult = false,
        backdropPath = "/f1AQhx6ZfGhPZFTVKgxG91PhEYc.jpg",
        genreIds = listOf(
            36,
            10752,
            18
        ),
        id = 753342,
        originalLanguage = "en",
        originalTitle = "Napoleon",
        overview = "An epic that details the checkered rise and fall of French Emperor Napoleon Bonaparte and his relentless journey to power through the prism of his addictive, volatile relationship with his wife, Josephine.",
        popularity = 2367.318,
        posterPath = "/jE5o7y9K6pZtWNNMEw3IdpHuncR.jpg",
        releaseDate = "2023-11-22",
        title = "Napoleon",
        voteAverage = 6.5,
        voteCount = 1193,
        isFavorite = true,
        video = false
    )
    val state by remember {
        mutableStateOf(
            DetailState(
                movie = movie
            )
        )
    }

    ComposeCourseTheme {
        DetailScreen(
            state,
            {
                movie = movie.copy(isFavorite = !movie.isFavorite)
            }
        )
    }
}
