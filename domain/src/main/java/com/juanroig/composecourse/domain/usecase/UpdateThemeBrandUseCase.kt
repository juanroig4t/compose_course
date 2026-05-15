package com.juanroig.composecourse.domain.usecase

import com.juanroig.composecourse.domain.model.userPreferences.ThemeBrand
import com.juanroig.composecourse.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class UpdateThemeBrandUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(themeBrand: ThemeBrand) {
        userPreferencesRepository.setThemeBrand(themeBrand)
    }
}
