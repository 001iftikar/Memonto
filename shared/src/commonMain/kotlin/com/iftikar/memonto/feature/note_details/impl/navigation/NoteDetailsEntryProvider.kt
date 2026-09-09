package com.iftikar.memonto.feature.note_details.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.iftikar.memonto.feature.edit_note.api.EditNoteKey
import com.iftikar.memonto.feature.note_details.api.NoteDetailKey
import com.iftikar.memonto.feature.note_details.impl.NoteDetailsScreen
import com.iftikar.memonto.feature.note_details.impl.NoteDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

fun EntryProviderScope<NavKey>.noteDetailsEntyProvider(
    backStack: NavBackStack<NavKey>
) {
    entry<NoteDetailKey> { key ->
        val viewModel = koinViewModel<NoteDetailsViewModel>()
        NoteDetailsScreen(
            noteId = key.id,
            noteDetailsViewModel = viewModel,
            onEditClick = { backStack.add(EditNoteKey(it)) }
        )
    }
}