package dev.catrambonem.marvelapp.domain.model

data class Character(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnailUrl: String? = null
)
