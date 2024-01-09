package com.juanroig.composecourse.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.ui.extension.toYear

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    goToDetail: (movieId: Int) -> Unit
) {

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        TopTenContent(state, goToDetail)

        PopularMoviesContent(state, goToDetail, viewModel::onFavoriteClick)

    }
}

@Composable
private fun PopularMoviesContent(
    state: HomeState,
    goToDetailMovie: (movieId: Int) -> Unit,
    onFavoriteClick: (movie: Movie) -> Unit
) {
    Text(text = "Populares", modifier = Modifier.padding(8.dp))

    LazyColumn() {
        itemsIndexed(state.popularMovies) { index, movie ->
            PopularMovieItem(movie, goToDetailMovie, onFavoriteClick)
        }
    }
}

@Composable
private fun PopularMovieItem(
    movie: Movie,
    goToDetailMovie: (movieId: Int) -> Unit,
    onFavoriteClick: (movie: Movie) -> Unit
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
                                    color = getColorByRating(movie.voteAverage),
                                    fontSize = 20.sp
                                )
                            ) {
                                append("%.2f".format(movie.voteAverage))
                            }
                        }
                    )

                    IconButton(
                        onClick = {
                            onFavoriteClick(movie)
                        }
                    ) {
                        // Change icon and tint if is favorite
                        if (movie.isFavorite) {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "Fav icon",
                                tint = Color.Red
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Fav icon"
                            )
                        }
                    }

                }
            }
        }
    }
}

fun getColorByRating(voteAverage: Double): Color {
    return if (voteAverage >= 7.0) {
        Color(0xFF008000)
    } else if (voteAverage >= 5.0) {
        Color(0xFF808000)
    } else {
        Color(0xFF800000)
    }
}

@Composable
private fun TopTenContent(
    state: HomeState,
    goToDetailMovie: (movieId: Int) -> Unit
) {
    Text(text = "Top 10", modifier = Modifier.padding(8.dp))
    LazyRow() {
        itemsIndexed(state.topTenMovies) { index, movie ->
            RowTopTenMovieItem(movie, index + 1, goToDetailMovie )
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
            .size(width = 150.dp, height = 200.dp)
            .clickable {
                goToDetailMovie(movie.id)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
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