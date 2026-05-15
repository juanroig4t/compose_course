package com.juanroig.composecourse.domain.repository

import com.juanroig.composecourse.domain.model.userPreferences.DarkThemeConfig
import com.juanroig.composecourse.domain.model.userPreferences.ThemeBrand
import com.juanroig.composecourse.domain.model.userPreferences.UserData
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun getUserData(): Flow<UserData>

    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
}
