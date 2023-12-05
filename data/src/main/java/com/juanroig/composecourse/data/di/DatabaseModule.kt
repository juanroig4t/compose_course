package com.juanroig.composecourse.data.di

import android.content.Context
import androidx.room.Room
import com.juanroig.composecourse.data.datasource.local.db.MovieDatabase
import com.juanroig.composecourse.data.datasource.local.db.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val APP_DB_NAME = "movie_db"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext ctx: Context
    ): MovieDatabase = Room.databaseBuilder(
        ctx,
        MovieDatabase::class.java,
        APP_DB_NAME
    ).build()

    @Provides
    @Singleton
    fun providePrinterDao(db: MovieDatabase): MovieDao = db.movieDao()

}
