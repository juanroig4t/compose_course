package com.juanroig.composecourse.domain.usecase

import com.juanroig.composecourse.domain.model.userPreferences.UserData
import com.juanroig.composecourse.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserDataUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<UserData> {
        return userPreferencesRepository.getUserData()
    }
}
