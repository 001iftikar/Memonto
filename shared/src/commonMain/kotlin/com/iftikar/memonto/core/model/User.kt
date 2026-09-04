package com.iftikar.memonto.core.model

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val id: Long,
    val name: String
)
