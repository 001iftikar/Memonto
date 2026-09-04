package com.iftikar.memonto.core.di

import com.iftikar.memonto.core.domain.repository.LocalNoteRepository
import com.iftikar.memonto.core.domain.repository.UtilRepository
import com.iftikar.memonto.feature.add_note.impl.AddNoteViewModel
import com.iftikar.memonto.feature.global.GlobalViewModel
import com.iftikar.memonto.feature.home.impl.HomeViewModel
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class ViewModelModule {
    @KoinViewModel
    fun addNoteViewModel(repo: LocalNoteRepository) = AddNoteViewModel(repo)

    @KoinViewModel
    fun homeViewModel(repo: LocalNoteRepository) = HomeViewModel(repo)

    @KoinViewModel
    fun globalViewModel(repo: UtilRepository) = GlobalViewModel(repo)
}