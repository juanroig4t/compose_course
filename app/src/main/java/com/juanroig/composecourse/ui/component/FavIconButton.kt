package com.juanroig.composecourse.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.juanroig.composecourse.domain.model.movie.Movie

@Composable
fun FavIconButton(
    onFavoriteClick: (movie: Movie) -> Unit,
    movie: Movie
) {
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