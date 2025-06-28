package dev.catrambonem.marvelapp.domain.repository

import dev.catrambonem.marvelapp.domain.model.Character

interface CharacterRepository {
    suspend fun getCharacters(): List<Character>
    suspend fun getCachedCharacters(): List<Character>
}