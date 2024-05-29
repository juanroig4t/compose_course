package com.juanroig.composecourse.ui.screen.favScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.ui.component.FavIconButton
import com.juanroig.composecourse.ui.extension.getColorByRating
import com.juanroig.composecourse.ui.extension.toYear

@Composable
fun FavScreen(
    viewModel: FavViewModel = hiltViewModel(),
    goToDetailMovie: (movieId: Int) -> Unit
){

    LazyColumn {
        items(viewModel.state.listFavMovies) {
            Text(
                text = it.title,
                modifier = Modifier.padding(16.dp).clickable {
                    goToDetailMovie(it.id)
                                                             },
            )
        }
    }
 }
