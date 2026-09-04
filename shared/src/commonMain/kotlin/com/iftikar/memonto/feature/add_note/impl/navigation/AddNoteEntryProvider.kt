package com.iftikar.memonto.feature.add_note.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.iftikar.memonto.feature.add_note.impl.AddNoteScreen
import com.iftikar.memonto.feature.add_note.impl.AddNoteViewModel
import com.iftikar.memonto.feature.bottom_navigation.Add
import org.koin.compose.viewmodel.koinViewModel

fun EntryProviderScope<NavKey>.addNoteEntryProvider(
    backStack: NavBackStack<NavKey>,
    showError:(String) -> Unit
) {
    entry<Add> {
        val viewModel: AddNoteViewModel = koinViewModel()
        AddNoteScreen(
            onCloseClick = { backStack.removeLastOrNull() },
            viewModel = viewModel,
            showError = showError
        )
    }
}