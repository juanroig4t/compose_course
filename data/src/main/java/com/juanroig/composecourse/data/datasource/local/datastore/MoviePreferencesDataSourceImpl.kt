package com.juanroig.composecourse.data.datasource.local.datastore

import com.juanroig.composecourse.data.datasource.local.datastore.common.AppDataStore
import com.juanroig.composecourse.data.datasource.local.datastore.common.DARK_THEME_CONFIG
import com.juanroig.composecourse.data.datasource.local.datastore.common.THEME_BRAND
import com.juanroig.composecourse.domain.model.userPreferences.DarkThemeConfig
import com.juanroig.composecourse.domain.model.userPreferences.ThemeBrand
import com.juanroig.composecourse.domain.model.userPreferences.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface MoviePreferencesDataSource {

    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
    fun getUserData(): Flow<UserData>
}

class MoviePreferencesDataSourceImpl @Inject constructor(
    private val dataStore: AppDataStore
) : MoviePreferencesDataSource {

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        dataStore.setValue(THEME_BRAND, themeBrand.name)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        dataStore.setValue(DARK_THEME_CONFIG, darkThemeConfig.name)
    }

    override fun getUserData(): Flow<UserData> {
        val themeBrand = dataStore.getValueFlow(THEME_BRAND, ThemeBrand.DEFAULT.name)
        val darkTheme =
            dataStore.getValueFlow(DARK_THEME_CONFIG, DarkThemeConfig.FOLLOW_SYSTEM.name)

        return themeBrand.combine(darkTheme) { themeBrand, darkTheme ->
            UserData(
                ThemeBrand.valueOf(themeBrand),
                DarkThemeConfig.valueOf(darkTheme)
            )
        }
    }
}
