package dev.catrambonem.marvelapp.data.repository

import dev.catrambonem.marvelapp.data.local.CharacterLocalDataSource
import dev.catrambonem.marvelapp.domain.model.Character
import dev.catrambonem.marvelapp.data.network.MarvelCharactersClient
import dev.catrambonem.marvelapp.domain.repository.CharacterRepository
import dev.catrambonem.marvelapp.util.getMarvelPrivateKey
import dev.catrambonem.marvelapp.util.getMarvelPublicKey
import dev.catrambonem.marvelapp.util.md5
import io.ktor.util.date.getTimeMillis

class CharacterRepositoryImpl(
    private val api: MarvelCharactersClient,
    private val local: CharacterLocalDataSource
) : CharacterRepository {

    override suspend fun getCharacters(): List<Character> {
            val timestamp = getTimeMillis()
            val publicKey = getMarvelPublicKey()
            val privateKey = getMarvelPrivateKey()
            val hash = ("$timestamp$privateKey$publicKey").md5()

            val apiResponse =
                api.getAllCharacters(timestamp = timestamp, md5 = hash, publicKey = publicKey)
            val charactersList = apiResponse.characters.list.map { characterResult ->
                Character(
                    id = characterResult.id,
                    name = characterResult.name,
                    description = characterResult.description,
                    thumbnailUrl = characterResult.thumbnail.url
                )
            }

            charactersList.forEach { local.insertCharacter(it) }
            return sort(charactersList)
    }

    override suspend fun getCachedCharacters(): List<Character> {
        return local.getAllCharacters()
    }

    private fun sort(characters: List<Character>): List<Character> {
        return characters.sortedWith(CharacterComparator())
    }

    private class CharacterComparator : Comparator<Character> {
        override fun compare(a: Character, b: Character): Int {
            if (a.description.isEmpty() && b.description.isEmpty()) {
                return b.id.compareTo(a.id)
            }
            if (a.description.isNotEmpty() && b.description.isNotEmpty()) {
                return a.id.compareTo(b.id)
            }
            if (a.description.isNotEmpty() && b.description.isEmpty()) {
                return -1
            }
            return 1
        }
    }
}