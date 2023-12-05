package com.juanroig.composecourse.domain.model.userPreferences

data class UserData(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val isLogged: Boolean = false
)