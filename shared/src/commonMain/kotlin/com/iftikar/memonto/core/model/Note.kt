package com.iftikar.memonto.core.model

import androidx.compose.runtime.Immutable

@Immutable
data class Note(
    val id: Long,
    val title: String,
    val body: String,
    val relationTo: String?
)
