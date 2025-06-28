package dev.catrambonem.marvelapp.data.di

import dev.catrambonem.marvelapp.data.repository.CharacterRepositoryImpl
import dev.catrambonem.marvelapp.data.network.MarvelCharactersClient
import dev.catrambonem.marvelapp.domain.repository.CharacterRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {

    // Cliente de red
    single { MarvelCharactersClient(get()) }

    // Repositorio
    single<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }
}