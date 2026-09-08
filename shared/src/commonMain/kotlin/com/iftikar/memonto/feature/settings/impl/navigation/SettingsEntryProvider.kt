package com.iftikar.memonto.feature.settings.impl.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.iftikar.memonto.feature.bottom_navigation.Settings
import com.iftikar.memonto.feature.settings.impl.Settings
import com.iftikar.memonto.feature.settings.impl.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

fun EntryProviderScope<NavKey>.settingsEntryProvider(
    listState: LazyListState,
    isUsernameFinding: () -> Boolean,
    userName: () -> String?,
    onUserNameChange: (String) -> Unit
) {
    entry<Settings> {
        val viewModel = koinViewModel<SettingsViewModel>()
        Settings(
            viewModel = viewModel,
            listState = listState,
            isUsernameFinding = isUsernameFinding(),
            userName = userName(),
            onUserNameSave = onUserNameChange
        )
    }
}