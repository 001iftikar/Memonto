package com.iftikar.memonto.feature.settings.impl

data class SettingsScreenState(
    val userName: String = "",
    val isOnDarkTheme: Boolean = false
)

sealed interface SettingsScreenAction {
    data class OnUserNameChange(val name: String) : SettingsScreenAction
    data object OnThemeToggle : SettingsScreenAction
}