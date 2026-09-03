package com.iftikar.memonto.feature.add_note.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.iftikar.memonto.feature.add_note.impl.AddNoteScreen
import com.iftikar.memonto.feature.bottom_navigation.Add

fun EntryProviderScope<NavKey>.addNoteEntryProvider(
    backStack: NavBackStack<NavKey>
) {
    entry<Add> {
        AddNoteScreen(
            onCloseClick = { backStack.removeLastOrNull() },
            onSaveClick = { backStack.removeLastOrNull() },
        )
    }
}