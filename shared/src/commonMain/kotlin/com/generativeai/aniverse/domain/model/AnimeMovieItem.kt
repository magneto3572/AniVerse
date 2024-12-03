package com.generativeai.aniverse.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class AnimeMovieItem(
    val animeUrl: String,
    val id: String,
    val img: String,
    val name: String,
    val releasedYear: String
)