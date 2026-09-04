package com.iftikar.memonto.feature.home.impl.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.iftikar.memonto.feature.bottom_navigation.Home
import com.iftikar.memonto.feature.home.impl.HomeScreen
import com.iftikar.memonto.feature.home.impl.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

fun EntryProviderScope<NavKey>.homeEntryProvider(
    listState: LazyListState,
    showError: (String) -> Unit,
    backStack: NavBackStack<NavKey>
) {
    entry<Home> {
        val viewModel = koinViewModel<HomeViewModel>()
        HomeScreen(
            listState = listState,
            viewModel = viewModel,
            showError = showError
        )
    }
}