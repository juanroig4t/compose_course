package com.juanroig.composecourse.data.di

import com.juanroig.composecourse.data.repository.MovieRepositoryImp
import com.juanroig.composecourse.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module that provides Repository dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepository: MovieRepositoryImp
    ): MovieRepository

}
