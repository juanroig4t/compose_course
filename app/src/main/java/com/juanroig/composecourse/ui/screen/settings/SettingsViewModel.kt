package com.juanroig.composecourse.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanroig.composecourse.domain.model.userPreferences.DarkThemeConfig
import com.juanroig.composecourse.domain.model.userPreferences.ThemeBrand
import com.juanroig.composecourse.domain.usecase.ObserveUserDataUseCase
import com.juanroig.composecourse.domain.usecase.UpdateDarkThemeConfigUseCase
import com.juanroig.composecourse.domain.usecase.UpdateThemeBrandUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    observeUserData: ObserveUserDataUseCase,
    private val updateThemeBrand: UpdateThemeBrandUseCase,
    private val updateDarkThemeConfig: UpdateDarkThemeConfigUseCase
) : ViewModel() {
    val uiState: StateFlow<SettingsState> =
        observeUserData()
            .map { userData ->
                SettingsState(
                    themeBrand = userData.themeBrand,
                    darkThemeConfig = userData.darkThemeConfig
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsState()
            )

    fun onThemeBrandClick(themeBrand: ThemeBrand) {
        viewModelScope.launch {
            updateThemeBrand(themeBrand)
        }
    }

    fun onDarkThemeConfigClick(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            updateDarkThemeConfig(darkThemeConfig)
        }
    }
}
