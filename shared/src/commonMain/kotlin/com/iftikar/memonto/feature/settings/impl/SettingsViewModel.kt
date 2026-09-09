package com.iftikar.memonto.feature.settings.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.memonto.core.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val themeRepository: ThemeRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsScreenState())
    val state = _state.asStateFlow()

    val isOnDarkTheme: StateFlow<Boolean?> = themeRepository.isInDarkTheme()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    init {
        viewModelScope.launch {
            isOnDarkTheme.collect { isDark ->
                _state.update { it.copy(isOnDarkTheme = isDark ?: false) }
            }
        }
    }
    fun onAction(action: SettingsScreenAction) {
        when(action) {
            is SettingsScreenAction.OnUserNameChange -> _state.update { it.copy(userName = action.name) }
            SettingsScreenAction.OnThemeToggle -> {
                viewModelScope.launch {
                    themeRepository.toggleDarkTheme()
                }
            }
        }
    }

    fun setUserName(userName: String) {
        _state.update { it.copy(userName = userName) }
    }
}