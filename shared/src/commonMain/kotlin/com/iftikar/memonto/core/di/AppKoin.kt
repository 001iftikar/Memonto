package com.iftikar.memonto.core.di

import org.koin.core.annotation.KoinApplication

@KoinApplication(
    modules = [AppModule::class, ViewModelModule::class, RepositoryModule::class]
)
class AppKoin
