package com.iftikar.memonto.core.local

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<NoteDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("memonto.db")
    return Room.databaseBuilder<NoteDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}