package com.generativeai.aniverse.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class RecentRelease(
    val episodeId: String,
    val episodeNo: Double,
    val episodeUrl: String,
    val id: String,
    val img: String,
    val name: String,
    val subOrDub: String
)
