package dev.catrambonem.marvelapp

import dev.catrambonem.marvelapp.data.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}