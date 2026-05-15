package com.juanroig.composecourse.domain.usecase

import com.juanroig.composecourse.domain.model.userPreferences.DarkThemeConfig
import com.juanroig.composecourse.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class UpdateDarkThemeConfigUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(darkThemeConfig: DarkThemeConfig) {
        userPreferencesRepository.setDarkThemeConfig(darkThemeConfig)
    }
}
