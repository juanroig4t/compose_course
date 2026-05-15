package com.juanroig.composecourse.data.repository

import com.juanroig.composecourse.data.datasource.local.datastore.MoviePreferencesDataSource
import com.juanroig.composecourse.domain.model.userPreferences.DarkThemeConfig
import com.juanroig.composecourse.domain.model.userPreferences.ThemeBrand
import com.juanroig.composecourse.domain.model.userPreferences.UserData
import com.juanroig.composecourse.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultUserPreferencesRepository @Inject constructor(
    private val preferencesDataSource: MoviePreferencesDataSource
) : UserPreferencesRepository {
    override fun getUserData(): Flow<UserData> {
        // DataStore queda escondido detras del repositorio: la UI aprende preferencias,
        // no detalles de almacenamiento.
        return preferencesDataSource.getUserData()
    }

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        preferencesDataSource.setThemeBrand(themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        preferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }
}
