package com.juanroig.composecourse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanroig.composecourse.domain.model.userPreferences.DarkThemeConfig
import com.juanroig.composecourse.domain.model.userPreferences.ThemeBrand
import com.juanroig.composecourse.domain.model.userPreferences.UserData
import com.juanroig.composecourse.domain.usecase.ObserveUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
    @Inject
    constructor(
        observeUserData: ObserveUserDataUseCase
    ) : ViewModel() {
        var showSplashScreen by mutableStateOf(true)
            private set

        val userData: StateFlow<UserData> =
            observeUserData()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue =
                        UserData(
                            themeBrand = ThemeBrand.DEFAULT,
                            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM
                        )
                )

        init {
            viewModelScope.launch {
                delay(200)
                showSplashScreen = false
            }
        }
    }
