package com.iftikar.memonto.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun isInDarkTheme(): Flow<Boolean?>
    suspend fun toggleDarkTheme()
}