package com.iftikar.memonto.feature.bottom_navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface BottomNav : NavKey

@Serializable data object Home : BottomNav
@Serializable data object Add : BottomNav
@Serializable data object Settings : BottomNav