package com.iftikar.memonto.feature.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.memonto.core.domain.repository.UtilRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class GlobalViewModel(
    private val utilRepository: UtilRepository
) : ViewModel() {
    private val _showUsernameState = MutableStateFlow(ShowUserNameState())
    val showUsernameState = _showUsernameState.asStateFlow()
    private val timeTicker: Flow<Long> = flow {
        while (true) {
            emit(Clock.System.now().toEpochMilliseconds())
            delay(1.minutes)
        }
    }
    val currentTime: StateFlow<Long> = timeTicker.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Clock.System.now().toEpochMilliseconds()
    )

    init {
        viewModelScope.launch {
            _showUsernameState.update { it.copy(isLoading = true) }
            delay(3.seconds)
            utilRepository.getUser().collect { user ->
                _showUsernameState.update {
                    it.copy(user = user, isLoading = false)
                }
            }
        }
    }

    fun changeUserNameFromSettings(userName: String) {
        viewModelScope.launch {
            utilRepository.saveUser(userName)
        }
    }
}



















