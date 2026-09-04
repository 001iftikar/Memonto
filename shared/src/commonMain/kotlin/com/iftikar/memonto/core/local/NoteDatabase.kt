package com.iftikar.memonto.core.local

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.iftikar.memonto.core.local.dao.NoteDao
import com.iftikar.memonto.core.local.dao.UserDao
import com.iftikar.memonto.core.local.model.NoteEntity
import com.iftikar.memonto.core.local.model.UserEntity

@Database(entities = [NoteEntity::class, UserEntity::class], version = 2, exportSchema = true)
@ConstructedBy(NoteDatabaseConstructor::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun userDao(): UserDao
}