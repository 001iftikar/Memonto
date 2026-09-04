package com.iftikar.memonto.feature.add_note.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.memonto.core.domain.repository.LocalNoteRepository
import com.iftikar.memonto.core.result.LocalError
import com.iftikar.memonto.core.result.onError
import com.iftikar.memonto.core.result.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val localNoteRepository: LocalNoteRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AddNoteScreenState())
    val state = _state.asStateFlow()

    private val _event: Channel<AddNoteEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onAction(action: AddNoteScreenAction) {
        when(action) {
            is AddNoteScreenAction.OnBodyChange -> _state.update { it.copy(body = action.body) }
            is AddNoteScreenAction.OnRelatedToChange -> {
                val relatedTo = action.relatedTo.ifEmpty { null }
                _state.update { it.copy(relatedTo = relatedTo) }
            }
            is AddNoteScreenAction.OnTitleChange -> _state.update { it.copy(title = action.title) }
            AddNoteScreenAction.OnSaveClick -> saveNote()
        }
    }

    private fun saveNote() {
        val currentState = _state.value
        viewModelScope.launch {
            localNoteRepository.saveNote(
                title = currentState.title.trim(),
                body = currentState.body,
                relatedTo = currentState.relatedTo?.trim()
            ).onSuccess {
                _event.send(AddNoteEvent.OnSuccess)
            }.onError { ex ->
                when(ex) {
                    LocalError.STORAGE_FULL -> {_event.send(AddNoteEvent.ShowError("Could not add note. If this error persists, please check your device storage."))}
                    else -> {_event.send(AddNoteEvent.ShowError("Oops! Something happened, please try again."))}
                }
            }
        }
    }
}









