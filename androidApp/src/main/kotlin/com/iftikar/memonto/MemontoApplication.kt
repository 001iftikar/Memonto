package com.iftikar.memonto

import android.app.Application
import androidx.datastore.core.DataStore
import com.iftikar.memonto.core.datastore.createDataStore
import com.iftikar.memonto.core.di.AppKoin
import com.iftikar.memonto.core.local.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.plugin.module.dsl.startKoin
import androidx.datastore.preferences.core.Preferences

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
                    single<DataStore<Preferences>> {
                        createDataStore(this@MemontoApplication)
                    }
                }
            )
        }
    }
}