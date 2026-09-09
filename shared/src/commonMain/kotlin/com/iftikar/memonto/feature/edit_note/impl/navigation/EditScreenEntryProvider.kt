package com.iftikar.memonto.feature.edit_note.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.iftikar.memonto.feature.edit_note.api.EditNoteKey
import com.iftikar.memonto.feature.edit_note.impl.EditNoteScreen
import com.iftikar.memonto.feature.edit_note.impl.EditNoteViewModel
import org.koin.compose.viewmodel.koinViewModel

fun EntryProviderScope<NavKey>.editNoteEntryProvider(
    backStack: NavBackStack<NavKey>,
    onShowError: (String) -> Unit
) {
    entry<EditNoteKey> {  key ->
        val viewModel = koinViewModel<EditNoteViewModel>()
        EditNoteScreen(
            onCloseClick = { backStack.removeLastOrNull() },
            showError = onShowError,
            noteId = key.id,
            viewModel = viewModel
        )
    }
}