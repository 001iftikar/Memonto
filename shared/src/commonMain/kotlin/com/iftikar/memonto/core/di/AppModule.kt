package com.iftikar.memonto.core.di

import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.iftikar.memonto.core.local.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Module
class AppModule {

    @Single
    fun provideNoteDatabase(
        @Provided
        builder: RoomDatabase.Builder<NoteDatabase>): NoteDatabase {
        return builder
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}