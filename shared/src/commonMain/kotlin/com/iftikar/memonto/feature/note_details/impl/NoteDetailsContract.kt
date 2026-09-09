package com.iftikar.memonto.feature.note_details.impl

import com.iftikar.memonto.core.model.Note

data class NoteDetailsState(
    val note: Note? = null,
    val error: String? = null
)

sealed interface NoteDetailsScreenAction {

}

sealed interface NoteDetailsScreenEvent {

}