package com.generativeai.aniverse.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class AnimeResponseDTO(
    val genres: List<String>,
    val onGoingSeries: List<OnGoingSeryDTO>,
    val recentReleases: List<RecentReleaseDTO>,
    val recentlyAddedSeries: List<RecentyAddedSeryDTO>
)