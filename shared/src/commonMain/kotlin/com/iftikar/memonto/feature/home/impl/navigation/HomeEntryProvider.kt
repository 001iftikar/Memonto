package com.iftikar.memonto.feature.home.impl.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.iftikar.memonto.feature.bottom_navigation.Home
import com.iftikar.memonto.feature.home.impl.HomeScreen

fun EntryProviderScope<NavKey>.homeEntryProvider(
    listState: LazyListState,
    backStack: NavBackStack<NavKey>
) {
    entry<Home> {
        HomeScreen(
            listState = listState
        )
    }
}