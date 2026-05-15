package com.juanroig.composecourse.domain.usecase

import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.domain.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        // Use case didactico: cuando una accion expresa una regla de negocio,
        // el ViewModel delega la decision y solo comunica la intencion del usuario.
        movieRepository.updateFavorite(
            movieId = movie.id,
            isFavorite = !movie.isFavorite
        )
    }
}
