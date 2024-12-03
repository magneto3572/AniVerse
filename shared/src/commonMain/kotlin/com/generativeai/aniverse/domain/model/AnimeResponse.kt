package com.generativeai.aniverse.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AnimeResponse(
    val genres: List<String>,
    val onGoingSeries: List<OnGoingSery>,
    val recentReleases: List<RecentRelease>,
    val recentlyAddedSeries: List<RecentyAddedSery>
)