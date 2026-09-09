package com.iftikar.memonto.feature.edit_note.impl

data class EditNoteScreenState(
    val title: String = "",
    val relatedTo: String = "",
    val body: String = ""
) {
    val enableSave: Boolean
        get() = title.isNotEmpty() && body.isNotEmpty()
}

sealed interface EditNoteScreenAction {
    data class OnTitleChange(val title: String) : EditNoteScreenAction
    data class OnBodyChange(val body: String) : EditNoteScreenAction
    data class OnRelatedToChange(val relatedTo: String) : EditNoteScreenAction
    data class OnSaveClick(val id: Long) : EditNoteScreenAction
}

sealed interface EditNoteScreenEvent {
    data class ShowError(val error: String) : EditNoteScreenEvent
    data object OnSuccess : EditNoteScreenEvent
}