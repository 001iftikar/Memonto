package com.iftikar.memonto.feature.edit_note.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class EditNoteKey(val id: Long) : NavKey
