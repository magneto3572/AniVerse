package com.generativeai.aniverse.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class OnGoingSeryDTO(
    val id: String,
    val img: String,
    val name: String
)
