package com.iftikar.memonto.core.di

import com.iftikar.memonto.core.local.getDatabaseBuilder
import org.koin.dsl.module
import org.koin.plugin.module.dsl.startKoin

fun initKoinIos() {

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