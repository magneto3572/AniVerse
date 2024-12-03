package com.generativeai.aniverse.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OnGoingSery(
    val id: String,
    val img: String,
    val name: String
)
