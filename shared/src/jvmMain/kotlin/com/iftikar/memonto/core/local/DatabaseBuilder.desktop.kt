package com.iftikar.memonto.core.local

import androidx.room3.Room
import androidx.room3.RoomDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<NoteDatabase> {

    val dbFile = File(
        System.getProperty("java.io.tmpdir"),
        "memonto.db"
    )

    return Room.databaseBuilder<NoteDatabase>(
        name = dbFile.absolutePath
    )
}