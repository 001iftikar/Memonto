package com.iftikar.memonto.feature.home.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.memonto.core.domain.repository.LocalNoteRepository
import com.iftikar.memonto.core.result.LocalError
import com.iftikar.memonto.core.result.onError
import com.iftikar.memonto.core.result.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class HomeViewModel(
    private val localNoteRepository: LocalNoteRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()
    private val _event: Channel<HomeScreenEvent> = Channel()
    val event = _event.receiveAsFlow()

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
        getNotes()
    }

    fun onAction(action: HomeScreenAction) {
        when (action) {
            is HomeScreenAction.OnLongPressed -> {
                _state.update { currentState ->
                    val contains = currentState.longPressedNotes.contains(action.id)
                    currentState.copy(
                        longPressedNotes = if (contains) currentState.longPressedNotes.minus(action.id) else currentState.longPressedNotes.plus(
                            action.id
                        )
                    )
                }
            }

            is HomeScreenAction.OnDeletePress -> deleteNote(action.id)
            is HomeScreenAction.OnPinPress -> pinNote(action.id)
            is HomeScreenAction.OnUnPinPress -> unPinNote(action.id)
        }
    }

    private fun getNotes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            delay(1.seconds)
            localNoteRepository.getNotes().collect { result ->
                result.onSuccess { notes ->
                    _state.update {
                        it.copy(notes = notes, error = null, isLoading = false)
                    }
                }.onError { ex ->
                    when (ex) {
                        LocalError.DATABASE_ERROR -> setError("Database error, please try again later and check for device storage if the error persists.")
                        else -> setError("Oops! Something went wrong.")
                    }
                }
            }
        }
    }

    private fun deleteNote(id: Long) {
        viewModelScope.launch {
            localNoteRepository.deleteNoteById(id).onSuccess {
                // do nothing
            }.onError { ex ->
                when (ex) {
                    LocalError.NOT_FOUND -> {
                        _event.send(HomeScreenEvent.ShowError("Note not found to delete"))
                    }

                    else -> {
                        _event.send(HomeScreenEvent.ShowError("Oops! Something went wrong."))
                    }
                }
            }
        }
    }

    private fun pinNote(id: Long) {
        viewModelScope.launch {
            localNoteRepository.pinNote(id).onSuccess {
                // do nothing
            }.onError { ex ->
                when (ex) {
                    LocalError.NOT_FOUND -> {
                        _event.send(HomeScreenEvent.ShowError("Note not found to pin"))
                    }

                    else -> {
                        _event.send(HomeScreenEvent.ShowError("Oops! Something went wrong."))
                    }
                }
            }
        }
    }

    private fun unPinNote(id: Long) {
        viewModelScope.launch {
            localNoteRepository.unPinNote(id).onSuccess {
                // do nothing
            }.onError { ex ->
                when (ex) {
                    LocalError.NOT_FOUND -> {
                        _event.send(HomeScreenEvent.ShowError("Note not found to unpin"))
                    }

                    else -> {
                        _event.send(HomeScreenEvent.ShowError("Oops! Something went wrong."))
                    }
                }
            }
        }
    }

    private fun setError(error: String) {
        _state.update { it.copy(error = error, isLoading = false) }
    }
}