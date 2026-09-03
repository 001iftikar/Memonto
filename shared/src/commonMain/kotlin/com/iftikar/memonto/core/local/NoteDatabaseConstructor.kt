package com.iftikar.memonto.core.local

import androidx.room3.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
expect object NoteDatabaseConstructor :
    RoomDatabaseConstructor<NoteDatabase> {

    override fun initialize(): NoteDatabase
}