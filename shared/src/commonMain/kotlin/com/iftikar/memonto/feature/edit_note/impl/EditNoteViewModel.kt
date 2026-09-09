package com.iftikar.memonto.feature.edit_note.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.memonto.core.domain.repository.LocalNoteRepository
import com.iftikar.memonto.core.result.onError
import com.iftikar.memonto.core.result.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditNoteViewModel(
    private val localNoteRepository: LocalNoteRepository
) : ViewModel() {
    private val _state = MutableStateFlow(EditNoteScreenState())
    val state = _state.asStateFlow()

    private val _event: Channel<EditNoteScreenEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onAction(action: EditNoteScreenAction) {
        when(action) {
            is EditNoteScreenAction.OnBodyChange -> _state.update { it.copy(body = action.body) }
            is EditNoteScreenAction.OnRelatedToChange -> _state.update { it.copy(relatedTo = action.relatedTo) }
            is EditNoteScreenAction.OnTitleChange -> _state.update { it.copy(title = action.title) }
            is EditNoteScreenAction.OnSaveClick -> editNote(action.id)
        }
    }

    fun writeNoteToState(id: Long) {
        viewModelScope.launch {
            localNoteRepository.getNoteById(id).collect { result ->
                result.onSuccess { note ->
                    _state.update {
                        it.copy(
                            title = note.title,
                            body = note.body,
                            relatedTo = note.relationTo ?: ""
                        )
                    }
                }.onError {
                    _event.send(EditNoteScreenEvent.ShowError("Note not found to edit."))
                }
            }
        }
    }

    private fun editNote(id: Long) {
        viewModelScope.launch {
            val currentState = _state.value
            localNoteRepository.updateNote(
                id = id,
                title = currentState.title,
                body = currentState.body,
                relatedTo = currentState.relatedTo.ifEmpty { null }
            ).onSuccess {
                _event.send(EditNoteScreenEvent.OnSuccess)
            }
                .onError {
                    _event.send(EditNoteScreenEvent.ShowError("Failed to update note."))
                }
        }
    }
}














