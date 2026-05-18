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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.juanroig.composecourse.domain.model.core.error.CustomFailure
import com.juanroig.composecourse.domain.model.core.error.Failure
import com.juanroig.composecourse.domain.model.core.error.NetworkFailure
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
    goToDetail: (movieId: Int) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val error = state.error

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToDetail -> goToDetail(effect.movieId)
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 4.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            TopTenContent(state, goToDetail)
        }

        item {
            SectionTitle(text = "Populares")
        }

        when {
            state.isLoading && state.popularMovies.isEmpty() -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            error != null && state.popularMovies.isEmpty() -> {
                item {
                    ErrorContent(
                        title = error.toHomeErrorTitle(),
                        message = error.toHomeErrorMessage(),
                        onRetryClick = viewModel::syncMovies
                    )
                }
            }

            state.popularMovies.isEmpty() -> {
                item {
                    Text(
                        text = "No hay peliculas disponibles.",
                        modifier = Modifier.padding(vertical = 24.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            else -> {
                items(state.popularMovies, key = { movie -> movie.id }) { movie ->
                    PopularMovieItem(movie, goToDetail, viewModel::setEvent)
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(
    title: String,
    message: String,
    onRetryClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        color = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Warning,
                contentDescription = null
            )
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Button(onClick = onRetryClick) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Reintentar")
            }
        }
    }
}

private fun Failure.toHomeErrorTitle(): String =
    when (this) {
        NetworkFailure.NoInternetConnection,
        NetworkFailure.UnknownHost -> "Sin conexion"

        NetworkFailure.Timeout -> "La conexion tarda demasiado"

        else -> "No se pudieron cargar las peliculas"
    }

private fun Failure.toHomeErrorMessage(): String =
    when (this) {
        NetworkFailure.NoInternetConnection,
        NetworkFailure.UnknownHost -> "Revisa tu conexion a internet y vuelve a intentarlo."

        NetworkFailure.Timeout -> "El servidor no ha respondido a tiempo. Prueba de nuevo en unos segundos."

        is NetworkFailure.ServerFailure,
        is NetworkFailure.JsonFormat,
        is NetworkFailure.UnexpectedNetworkError -> "Ahora mismo el servicio no esta disponible. Intentalo otra vez."

        CustomFailure.NoData,
        CustomFailure.NoResponse,
        CustomFailure.NotFound -> "No hemos encontrado peliculas para mostrar en este momento."

        else -> "Ha ocurrido un problema inesperado. Vuelve a intentarlo."
    }

@Composable
private fun PopularMovieItem(
    movie: Movie,
    goToDetailMovie: (movieId: Int) -> Unit,
    setEvent: (HomeEvent) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                goToDetailMovie(movie.id)
            }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(width = 104.dp, height = 156.dp)
                    .clip(MaterialTheme.shapes.medium),
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(156.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "${movie.title} (${movie.releaseDate.toYear()})",
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = movie.overview,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                    RatingPill(movie.voteAverage)

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
    SectionTitle(text = "Top 10")
    LazyRow(
        contentPadding = PaddingValues(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .size(width = 132.dp, height = 198.dp)
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.72f)
                            ),
                            startY = 80f
                        )
                    )
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = "#$index",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = movie.title,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
private fun RatingPill(voteAverage: Double) {
    Surface(
        color = voteAverage.getColorByRating().copy(alpha = 0.14f),
        contentColor = voteAverage.getColorByRating(),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append("Nota ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("%.2f".format(voteAverage))
                }
            },
            maxLines = 1,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
