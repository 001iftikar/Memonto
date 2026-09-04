package com.iftikar.memonto

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.iftikar.memonto.core.di.AppKoin
import org.koin.plugin.module.dsl.startKoin

fun main() = application {

    startKoin<AppKoin>()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Memonto"
    ) {
        App()
    }
}