package com.iftikar.memonto.feature.note_details.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class NoteDetailKey(val id: Long) : NavKey
