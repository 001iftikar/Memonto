package com.iftikar.memonto.feature.add_note.impl

data class AddNoteScreenState(
    val title: String = "",
    val body: String = "",
    val relatedTo: String? = null
) {
    val enableSave: Boolean
        get() = title.isNotEmpty() && body.isNotEmpty()
}

sealed interface AddNoteScreenAction {
    data class OnTitleChange(val title: String) : AddNoteScreenAction
    data class OnBodyChange(val body: String) : AddNoteScreenAction
    data class OnRelatedToChange(val relatedTo: String) : AddNoteScreenAction
    data object OnSaveClick : AddNoteScreenAction
}

sealed interface AddNoteEvent {
    data class ShowError(val error: String) : AddNoteEvent
    data object OnSuccess : AddNoteEvent
}