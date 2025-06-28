package dev.catrambonem.marvelapp.android.di

import app.cash.sqldelight.db.SqlDriver
import dev.catrambonem.marvelapp.cache.MarvelDatabase
import dev.catrambonem.marvelapp.data.local.DatabaseDriverFactory
import dev.catrambonem.marvelapp.data.local.CharacterLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDatabaseModule = module {
    single { DatabaseDriverFactory(androidContext()) }
    single<SqlDriver> {
        get<DatabaseDriverFactory>().createDriver()
    }
    single {
        MarvelDatabase( driver = get() )
    }
    single {
        CharacterLocalDataSource( get() )
    }
}