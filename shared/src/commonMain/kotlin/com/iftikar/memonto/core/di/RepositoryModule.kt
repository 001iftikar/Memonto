package com.iftikar.memonto.core.di

import com.iftikar.memonto.core.data.repository.LocalNoteRepositoryImpl
import com.iftikar.memonto.core.data.repository.UtilRepositoryImpl
import com.iftikar.memonto.core.domain.repository.LocalNoteRepository
import com.iftikar.memonto.core.domain.repository.UtilRepository
import com.iftikar.memonto.core.local.dao.NoteDao
import com.iftikar.memonto.core.local.dao.UserDao
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class RepositoryModule {
    @Factory(binds = [LocalNoteRepository::class])
    fun localNoteRepository(dao: NoteDao) = LocalNoteRepositoryImpl(dao)

    @Factory(binds = [UtilRepository::class])
    fun utilRepository(userDao: UserDao) = UtilRepositoryImpl(userDao)
}