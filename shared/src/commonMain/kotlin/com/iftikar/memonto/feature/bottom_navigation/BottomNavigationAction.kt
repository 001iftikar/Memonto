package com.iftikar.memonto.feature.bottom_navigation

sealed interface BottomNavigationAction {
    data object OnHomeClick : BottomNavigationAction
    data object OnAddClick : BottomNavigationAction
    data object OnSettingsClick : BottomNavigationAction
}