package com.juanroig.composecourse.ui.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juanroig.composecourse.domain.model.userPreferences.DarkThemeConfig
import com.juanroig.composecourse.domain.model.userPreferences.ThemeBrand

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SettingsSectionTitle("Tema de color")
        ThemeBrand.entries.forEach { themeBrand ->
            SettingsRadioRow(
                text = themeBrand.toLabel(),
                selected = state.themeBrand == themeBrand,
                onClick = { viewModel.onThemeBrandClick(themeBrand) }
            )
        }

        SettingsSectionTitle("Modo oscuro")
        DarkThemeConfig.entries.forEach { darkThemeConfig ->
            SettingsRadioRow(
                text = darkThemeConfig.toLabel(),
                selected = state.darkThemeConfig == darkThemeConfig,
                onClick = { viewModel.onDarkThemeConfigClick(darkThemeConfig) }
            )
        }
    }
}

@Composable
private fun SettingsSectionTitle(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
        )
    )
}

@Composable
private fun SettingsRadioRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun ThemeBrand.toLabel(): String =
    when (this) {
        ThemeBrand.DEFAULT -> "Colores dinamicos del sistema"
        ThemeBrand.ANDROID -> "Paleta fija de la app"
    }

private fun DarkThemeConfig.toLabel(): String =
    when (this) {
        DarkThemeConfig.FOLLOW_SYSTEM -> "Seguir sistema"
        DarkThemeConfig.LIGHT -> "Claro"
        DarkThemeConfig.DARK -> "Oscuro"
    }
