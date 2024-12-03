package com.generativeai.aniverse.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class PopularAnimeModelItemDTO(
    val animeUrl: String,
    val id: String,
    val img: String,
    val name: String,
    val releasedYear: String
)