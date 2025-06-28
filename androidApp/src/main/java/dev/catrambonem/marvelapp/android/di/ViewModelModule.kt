package dev.catrambonem.marvelapp.android.di

import dev.catrambonem.marvelapp.android.ui.CharactersViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { CharactersViewModel(get()) }
}