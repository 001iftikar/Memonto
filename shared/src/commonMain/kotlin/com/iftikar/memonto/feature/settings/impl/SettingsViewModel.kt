package com.iftikar.memonto.feature.settings.impl

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {
    private val _state = MutableStateFlow(SettingsScreenState())
    val state = _state.asStateFlow()

    fun onAction(action: SettingsScreenAction) {
        when(action) {
            is SettingsScreenAction.OnUserNameChange -> _state.update { it.copy(userName = action.name) }
        }
    }

    fun setUserName(userName: String) {
        _state.update { it.copy(userName = userName) }
    }
}