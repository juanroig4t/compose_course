package com.juanroig.composecourse.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.juanroig.composecourse.data.datasource.local.datastore.MoviePreferencesDataSource
import com.juanroig.composecourse.data.datasource.local.datastore.MoviePreferencesDataSourceImpl
import com.juanroig.composecourse.data.datasource.local.datastore.common.AppDataStore
import com.juanroig.composecourse.data.datasource.local.datastore.common.USER_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    fun provideAppDataStore(
        dataStore: DataStore<Preferences>
    ): AppDataStore {
        return AppDataStore(
            dataStore = dataStore
        )
    }

    @Provides
    @Singleton
    fun provideMoviePreferencesDataSource(
        dataStore: AppDataStore
    ): MoviePreferencesDataSource {
        return MoviePreferencesDataSourceImpl(dataStore = dataStore)
    }
}
