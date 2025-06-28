package dev.catrambonem.marvelapp.android

import android.app.Application
import dev.catrambonem.marvelapp.android.di.viewModelModule
import dev.catrambonem.marvelapp.android.di.localDatabaseModule
import dev.catrambonem.marvelapp.data.di.networkModule
import dev.catrambonem.marvelapp.data.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class MarvelApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MarvelApp)
            modules(listOf(networkModule, localDatabaseModule, repositoryModule, viewModelModule,))
        }
    }
}