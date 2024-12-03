package com.generativeai.aniverse.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class RecentReleaseDTO(
    val episodeId: String,
    val episodeNo: Double,
    val episodeUrl: String,
    val id: String,
    val img: String,
    val name: String,
    val subOrDub: String
)
