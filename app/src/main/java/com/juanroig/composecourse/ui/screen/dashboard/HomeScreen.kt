package com.juanroig.composecourse.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.ui.component.FavIconButton
import com.juanroig.composecourse.ui.extension.getColorByRating
import com.juanroig.composecourse.ui.extension.toYear
import com.juanroig.composecourse.ui.screen.dashboard.model.HomeEffect
import com.juanroig.composecourse.ui.screen.dashboard.model.HomeEvent
import com.juanroig.composecourse.ui.screen.dashboard.model.HomeState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    contentPadding: PaddingValues,
    goToDetail: (movieId: Int) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val popularMovies = viewModel.flowLifecycleState.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToDetail -> goToDetail(effect.movieId)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            TopTenContent(state, goToDetail)
        }

        item {
            Text(
                text = "Populares",
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        if (popularMovies is Result.Success) {
            items(popularMovies.data, key = { movie -> movie.id }) { movie ->
                PopularMovieItem(movie, goToDetail, viewModel::setEvent)
            }
        } else {
            item {
                Text(
                    text = "No hay peliculas disponibles.",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun PopularMovieItem(
    movie: Movie,
    goToDetailMovie: (movieId: Int) -> Unit,
    setEvent: (HomeEvent) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                goToDetailMovie(movie.id)
            }
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "${movie.title} (${movie.releaseDate.toYear()})",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 20.sp
                                )
                            ) {
                                append("Puntuacion: ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = movie.voteAverage.getColorByRating(),
                                    fontSize = 20.sp
                                )
                            ) {
                                append("%.2f".format(movie.voteAverage))
                            }
                        }
                    )

                    FavIconButton({ setEvent(HomeEvent.OnFavoriteClick(it)) }, movie)
                }
            }
        }
    }
}

@Composable
private fun TopTenContent(
    state: HomeState,
    goToDetailMovie: (movieId: Int) -> Unit
) {
    Text(text = "Top 10", modifier = Modifier.padding(horizontal = 8.dp))
    LazyRow {
        itemsIndexed(state.topTenMovies) { index, movie ->
            RowTopTenMovieItem(movie, index + 1, goToDetailMovie)
        }
    }
}

@Composable
private fun RowTopTenMovieItem(
    movie: Movie,
    index: Int,
    goToDetailMovie: (movieId: Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .padding(6.dp)
            .size(width = 100.dp, height = 150.dp)
            .clickable {
                goToDetailMovie(movie.id)
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(80.dp)
                    .padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(8.dp),
                    text = index.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}
