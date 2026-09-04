package com.iftikar.memonto.core.local.model

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.iftikar.memonto.core.model.Note

@Entity(
    tableName = "notes",
    indices = [
        Index("pinnedAt")
    ]
)
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val body: String,
    val relationTo: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val pinnedAt: Long? = null
)

fun NoteEntity.asExternalModel(): Note {
    return Note(
        id = id,
        title = title,
        body = body,
        relationTo = relationTo,
        createdAt = createdAt,
        updatedAt = updatedAt,
        pinnedAt = pinnedAt
    )
}
