package com.juanroig.composecourse.data.di

import com.juanroig.composecourse.data.repository.MovieRepositoryImp
import com.juanroig.composecourse.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindMovieRepository(
        movieRepository: MovieRepositoryImp
    ): MovieRepository

}
