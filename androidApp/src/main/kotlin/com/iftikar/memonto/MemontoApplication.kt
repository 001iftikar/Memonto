package com.iftikar.memonto

import android.app.Application
import com.iftikar.memonto.core.di.AppKoin
import com.iftikar.memonto.core.local.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.plugin.module.dsl.startKoin

class MemontoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin<AppKoin> {

            androidContext(this@MemontoApplication)

            modules(
                module {
                    single {
                        getDatabaseBuilder(
                            this@MemontoApplication
                        )
                    }
                }
            )
        }
    }
}