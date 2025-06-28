package dev.catrambonem.marvelapp.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.catrambonem.marvelapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
}

class CharactersViewModel(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CharactersUiState>(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    private val _events = Channel<UiEvent>()
    val events = _events.receiveAsFlow()

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            _uiState.value = CharactersUiState.Loading
            runCatching {
                repository.getCharacters()
            }.onSuccess { list ->
                _uiState.value = CharactersUiState.Success(list)
            }.onFailure { exception ->
                println(exception.message)

                _uiState.value = CharactersUiState.Loading
                val cache = repository.getCachedCharacters()
                if (cache.isEmpty()) {
                    _uiState.value = CharactersUiState.Error(
                        "Error al obtener la informacion, intente nuevamente mas tarde"
                    )
                } else {
                    _uiState.value = CharactersUiState.Success(cache)
                    _events.send(UiEvent.ShowToast("Mostrando datos sin conexión"))
                }
            }
        }
    }
}