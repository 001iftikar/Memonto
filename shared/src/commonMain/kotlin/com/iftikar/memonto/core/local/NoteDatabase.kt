package com.iftikar.memonto.core.local

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.iftikar.memonto.core.local.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = true)
@ConstructedBy(NoteDatabaseConstructor::class)
abstract class NoteDatabase : RoomDatabase() {
}