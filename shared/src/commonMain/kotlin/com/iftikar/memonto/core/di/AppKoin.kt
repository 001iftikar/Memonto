package com.iftikar.memonto.core.di

import org.koin.core.annotation.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import org.koin.plugin.module.dsl.startKoin

@KoinApplication(
    modules = [AppModule::class]
)
class AppKoin
