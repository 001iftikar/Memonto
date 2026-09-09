package com.iftikar.memonto.feature.note_details.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.memonto.core.domain.repository.LocalNoteRepository
import com.iftikar.memonto.core.result.LocalError
import com.iftikar.memonto.core.result.onError
import com.iftikar.memonto.core.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    private val localNoteRepository: LocalNoteRepository
) : ViewModel() {
    private val _state = MutableStateFlow(NoteDetailsState())
    val state = _state.asStateFlow()

    fun onAction(action: NoteDetailsScreenAction) {

    }

    fun getNoteById(id: Long) {
        _state.update { it.copy(error = null) }
        viewModelScope.launch {
            localNoteRepository.getNoteById(id).collect { result ->
                result.onSuccess { note ->
                    _state.update { it.copy(note = note) }
                }.onError { ex ->
                    when(ex) {
                        LocalError.NOT_FOUND -> _state.update { it.copy(error = "This note is not found.") }
                        else -> _state.update { it.copy(error = "Oops! Something went wrong, please check free storage if this persists.") }
                    }
                }
            }
        }
    }
}