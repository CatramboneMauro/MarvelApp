package dev.catrambonem.marvelapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CharactersResponse(
    @SerialName("data") val characters: CharacterData
)

@Serializable
data class CharacterData(
    @SerialName("results") val list: List<CharacterResult>
)

@Serializable
data class CharacterResult(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("thumbnail") val thumbnail: Thumbnail
)

@Serializable
data class Thumbnail(
    @SerialName("path") val path: String,
    @SerialName("extension") val extension: String
) {
    val url: String get() = "$path.$extension".replace("http://", "https://")
}
