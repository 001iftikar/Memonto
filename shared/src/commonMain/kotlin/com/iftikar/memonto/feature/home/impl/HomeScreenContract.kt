package com.iftikar.memonto.feature.home.impl

import com.iftikar.memonto.core.model.Note

data class HomeScreenState(
    val notes: List<Note> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val longPressedNotes: Set<Long> = emptySet(),
    val openEditNote: Boolean = false
)

data class EditNoteState(
    val id: Long? = null,
    val title: String = "",
    val relatedTo: String = "",
    val body: String = ""
)

sealed interface HomeScreenAction {
    data class OnLongPressed(val id: Long) : HomeScreenAction
    data class OnDeletePress(val id: Long) : HomeScreenAction
    data class OnPinPress(val id: Long) : HomeScreenAction
    data class OnUnPinPress(val id: Long) : HomeScreenAction
    data class OnEditPress(val id: Long) : HomeScreenAction
}

sealed interface EditNoteAction {
    data class OnTitleChange(val tittle: String) : EditNoteAction
    data class OnRelatedToChange(val relatedTo: String?) : EditNoteAction
    data class OnBodyChange(val body: String) : EditNoteAction
    data class OnSaveClick(val id: Long) : EditNoteAction
    data class OnCancel(val id: Long) : EditNoteAction
}

sealed interface HomeScreenEvent {
    data class ShowError(val error: String) : HomeScreenEvent
}