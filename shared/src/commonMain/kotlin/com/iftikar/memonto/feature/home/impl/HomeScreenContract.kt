package com.iftikar.memonto.feature.home.impl

import com.iftikar.memonto.core.model.Note

data class HomeScreenState(
    val notes: List<Note> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val longPressedNotes: Set<Long> = emptySet(),
)

sealed interface HomeScreenAction {
    data class OnLongPressed(val id: Long) : HomeScreenAction
    data class OnDeletePress(val id: Long) : HomeScreenAction
    data class OnPinPress(val id: Long) : HomeScreenAction
    data class OnUnPinPress(val id: Long) : HomeScreenAction
}

sealed interface HomeScreenEvent {
    data class ShowError(val error: String) : HomeScreenEvent
}