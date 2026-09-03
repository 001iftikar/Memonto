package com.iftikar.memonto.core.local.di

import com.iftikar.memonto.core.di.AppKoin
import com.iftikar.memonto.core.local.getDatabaseBuilder
import org.koin.dsl.module
import org.koin.plugin.module.dsl.startKoin

fun initKoinDesktop() {

    startKoin<AppKoin> {

        modules(
            module {
                single {
                    getDatabaseBuilder()
                }
            }
        )
    }
}