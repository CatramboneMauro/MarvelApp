package dev.catrambonem.marvelapp.data.local

import dev.catrambonem.marvelapp.cache.MarvelDatabase
import dev.catrambonem.marvelapp.domain.model.Character

class CharacterLocalDataSource(database: MarvelDatabase) {
    private val queries = database.marvelDatabaseQueries

    fun insertCharacter(character: Character) {
        queries.insertCharacter(
            character.id,
            character.name,
            character.description,
            character.thumbnailUrl
        )
    }

    fun getAllCharacters(): List<Character> {
        return queries.selectAllCharacters().executeAsList().map { entity ->
            Character(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                thumbnailUrl = entity.thumbnailUrl
            )
        }
    }
}