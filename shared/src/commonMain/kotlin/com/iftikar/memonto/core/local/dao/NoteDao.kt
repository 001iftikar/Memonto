package com.iftikar.memonto.core.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Query
import androidx.room3.Upsert
import com.iftikar.memonto.core.local.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query(
        """
        SELECT *
        FROM notes
        ORDER BY
            CASE WHEN pinnedAt IS NULL THEN 1 ELSE 0 END,
            pinnedAt DESC,
            updatedAt DESC
        """
    )
    fun observeNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getSingleNoteById(id: Long): Flow<NoteEntity?>

    @Upsert
    suspend fun saveNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}