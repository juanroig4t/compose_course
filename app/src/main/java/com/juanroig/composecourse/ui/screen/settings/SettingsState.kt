package com.juanroig.composecourse.ui.screen.settings

import com.juanroig.composecourse.domain.model.userPreferences.DarkThemeConfig
import com.juanroig.composecourse.domain.model.userPreferences.ThemeBrand

data class SettingsState(
    val themeBrand: ThemeBrand = ThemeBrand.DEFAULT,
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM
)
