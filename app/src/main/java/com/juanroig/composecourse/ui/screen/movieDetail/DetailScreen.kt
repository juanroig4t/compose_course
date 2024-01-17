package com.juanroig.composecourse.ui.screen.movieDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.juanroig.composecourse.domain.model.core.error.Failure
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.ui.component.FavIconButton
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = "https://image.tmdb.org/t/p/w500${movie.backdropPath}",
                    contentDescription = "backdrop Path",
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FavIconButton(onFavoriteClick, movie)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                HeaderContent(movie)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = movie.title)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = movie.overview)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = movie.releaseDate)

            }
        }

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

    if (state.error != null) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = state.error.data.toString())
        }
    }
}

@Composable
private fun HeaderContent(movie: Movie) {
    Spacer(modifier = Modifier.size(140.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Spacer(modifier = Modifier.size(4.dp))
        AsyncImage(
            modifier = Modifier
                .size(100.dp, 150.dp)
                .clip(MaterialTheme.shapes.medium),
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = "poster Path",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            text = movie.title,
            style = MaterialTheme.typography.titleLarge
        )
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
        DetailScreen( state,
            {
                movie = movie.copy(isFavorite = !movie.isFavorite)
            }
        )
    }
}
