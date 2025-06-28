package dev.catrambonem.marvelapp.android.ui

import dev.catrambonem.marvelapp.domain.model.Character


sealed class CharactersUiState {
    object Loading : CharactersUiState()
    data class Success(val characters: List<Character>) : CharactersUiState()
    data class Error(val message: String) : CharactersUiState()
}