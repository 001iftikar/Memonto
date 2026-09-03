package com.iftikar.memonto.feature.settings.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.iftikar.memonto.feature.bottom_navigation.Settings
import com.iftikar.memonto.feature.settings.impl.Settings

fun EntryProviderScope<NavKey>.settingsEntryProvider() {
    entry<Settings> {
        Settings()
    }
}