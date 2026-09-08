package com.iftikar.memonto.feature.settings.impl

data class SettingsScreenState(
    val userName: String = ""
)

sealed interface SettingsScreenAction {
    data class OnUserNameChange(val name: String) : SettingsScreenAction
}