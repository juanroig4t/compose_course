package com.juanroig.composecourse.ui.screen.favScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.ui.extension.getColorByRating
import com.juanroig.composecourse.ui.extension.toYear

@Composable
fun FavScreen(
    viewModel: FavViewModel = hiltViewModel(),
    goToDetailMovie: (movieId: Int) -> Unit
) {
    if (viewModel.state.listFavMovies.isEmpty()) {
        EmptyFavoritesContent()
        return
    }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        items(viewModel.state.listFavMovies, key = { movie -> movie.id }) {
            FavItem(
                movie = it,
                goToDetailMovie = goToDetailMovie
            )
        }
    }
}

@Composable
private fun EmptyFavoritesContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "No tienes peliculas favoritas",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Marca peliculas como favoritas para encontrarlas aqui.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun FavItem(
    movie: Movie,
    goToDetailMovie: (movieId: Int) -> Unit
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
        Row(
            modifier = Modifier
        ) {
            AsyncImage(
                modifier = Modifier
                    .weight(1f),
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .weight(3f)
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
                                append("Puntuación: ")
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

                    // FavIconButton(onFavoriteClick, movie)
                }
            }
        }
    }
}
