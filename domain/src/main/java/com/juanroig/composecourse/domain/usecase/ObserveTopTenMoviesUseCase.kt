package com.juanroig.composecourse.domain.usecase

import com.juanroig.composecourse.domain.model.core.result.Result
import com.juanroig.composecourse.domain.model.movie.Movie
import com.juanroig.composecourse.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTopTenMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Result<List<Movie>>> {
        // El prefijo Observe deja claro que este caso de uso no hace una lectura puntual:
        // mantiene un stream activo que se actualiza cuando cambia la fuente local.
        return movieRepository.getTopTenMovies()
    }
}
