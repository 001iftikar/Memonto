package com.iftikar.memonto.core.domain.repository

import com.iftikar.memonto.core.model.Note
import com.iftikar.memonto.core.result.EmptyResult
import com.iftikar.memonto.core.result.LocalError
import com.iftikar.memonto.core.result.Result
import kotlinx.coroutines.flow.Flow

interface LocalNoteRepository {
    fun getNotes(): Flow<Result<List<Note>, LocalError>>
    suspend fun saveNote(title: String, body: String, relatedTo: String?): EmptyResult<LocalError>
    suspend fun deleteNoteById(id: Long): EmptyResult<LocalError>
    suspend fun pinNote(id: Long) : EmptyResult<LocalError>
    suspend fun unPinNote(id: Long) : EmptyResult<LocalError>
}