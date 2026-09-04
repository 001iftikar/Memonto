package com.iftikar.memonto.core.data.repository

import androidx.sqlite.SQLiteException
import com.iftikar.memonto.core.domain.repository.LocalNoteRepository
import com.iftikar.memonto.core.local.dao.NoteDao
import com.iftikar.memonto.core.local.model.NoteEntity
import com.iftikar.memonto.core.local.model.asExternalModel
import com.iftikar.memonto.core.model.Note
import com.iftikar.memonto.core.result.EmptyResult
import com.iftikar.memonto.core.result.LocalError
import com.iftikar.memonto.core.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.time.Clock

class LocalNoteRepositoryImpl(
    private val noteDao: NoteDao
) : LocalNoteRepository {
    override fun getNotes(): Flow<Result<List<Note>, LocalError>> {
        return noteDao.observeNotes()
            .map { entityList ->
                val notes = entityList.map { it.asExternalModel() }
                Result.Success(notes) as Result<List<Note>, LocalError>
            }
            .catch { ex ->
                ex.printStackTrace()
                when (ex) {
                    is SQLiteException -> emit(Result.Error(LocalError.DATABASE_ERROR))
                    else -> emit(Result.Error(LocalError.UNKNOWN))
                }
            }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun saveNote(
        title: String,
        body: String,
        relatedTo: String?
    ): EmptyResult<LocalError> = withContext(Dispatchers.IO) {
        try {
            val now = Clock.System.now().toEpochMilliseconds()
            val note = NoteEntity(
                title = title,
                body = body,
                relationTo = relatedTo,
                createdAt = now,
                updatedAt = now
            )
            noteDao.saveNote(note)
            Result.Success(Unit)
        } catch (ex: SQLiteException) {
            ex.printStackTrace()
            Result.Error(LocalError.STORAGE_FULL)
        } catch (ex: Exception) {
            Result.Error(LocalError.UNKNOWN)
        }
    }

    override suspend fun deleteNoteById(id: Long): EmptyResult<LocalError> = withContext(Dispatchers.IO) {
        try {
            val noteToDelete = noteDao.getSingleNoteById(id) ?: return@withContext Result.Error(
                LocalError.NOT_FOUND
            )
            noteDao.deleteNote(noteToDelete)
            Result.Success(Unit)
        } catch (ex: SQLiteException) {
            ex.printStackTrace()
            Result.Error(LocalError.DATABASE_ERROR)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(LocalError.UNKNOWN)
        }
    }

    override suspend fun pinNote(id: Long): EmptyResult<LocalError> = withContext(Dispatchers.IO) {
        try {
            val now = Clock.System.now().toEpochMilliseconds()
            val noteToPin = noteDao.getSingleNoteById(id) ?: return@withContext Result.Error(
                LocalError.NOT_FOUND
            )
            noteDao.saveNote(noteToPin.copy(pinnedAt = now))
            Result.Success(Unit)
        } catch (ex: SQLiteException) {
            ex.printStackTrace()
            Result.Error(LocalError.DATABASE_ERROR)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(LocalError.UNKNOWN)
        }
    }

    override suspend fun unPinNote(id: Long): EmptyResult<LocalError> = withContext(Dispatchers.IO) {
        try {
            val noteToPin = noteDao.getSingleNoteById(id) ?: return@withContext Result.Error(
                LocalError.NOT_FOUND
            )
            noteDao.saveNote(noteToPin.copy(pinnedAt = null))
            Result.Success(Unit)
        } catch (ex: SQLiteException) {
            ex.printStackTrace()
            Result.Error(LocalError.DATABASE_ERROR)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(LocalError.UNKNOWN)
        }
    }
}














