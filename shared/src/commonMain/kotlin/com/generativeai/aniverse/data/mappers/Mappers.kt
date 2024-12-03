package com.generativeai.aniverse.data.mappers

import com.generativeai.aniverse.data.dto.AnimeMovieItemDTO
import com.generativeai.aniverse.data.dto.AnimeResponseDTO
import com.generativeai.aniverse.data.dto.OnGoingSeryDTO
import com.generativeai.aniverse.data.dto.PopularAnimeModelItemDTO
import com.generativeai.aniverse.data.dto.RecentReleaseDTO
import com.generativeai.aniverse.data.dto.RecentyAddedSeryDTO
import com.generativeai.aniverse.domain.model.AnimeMovieItem
import com.generativeai.aniverse.domain.model.AnimeResponse
import com.generativeai.aniverse.domain.model.OnGoingSery
import com.generativeai.aniverse.domain.model.PopularAnimeModelItem
import com.generativeai.aniverse.domain.model.RecentRelease
import com.generativeai.aniverse.domain.model.RecentyAddedSery


// Mapper for Anime Response
fun AnimeResponseDTO.toDomainAnimeResponse() : AnimeResponse {
    return AnimeResponse(
        genres = this.genres,
        onGoingSeries = this.onGoingSeries.toDomainOnGoingSeries(),
        recentReleases = this.recentReleases.toDomainRecentRelease(),
        recentlyAddedSeries = this.recentlyAddedSeries.toDomainRecentlyAddedSeries()
    )
}

fun List<OnGoingSeryDTO>.toDomainOnGoingSeries() : List<OnGoingSery> = map {
    OnGoingSery(
        id = it.id,
        img = it.img,
        name = it.name,
    )
}

fun List<RecentyAddedSeryDTO>.toDomainRecentlyAddedSeries() : List<RecentyAddedSery> = map {
    RecentyAddedSery(
        id = it.id,
        img = it.img,
        name = it.name,
    )
}


fun List<RecentReleaseDTO>.toDomainRecentRelease() : List<RecentRelease> = map {
    RecentRelease(
        id = it.id,
        img = it.img,
        name = it.name,
        episodeNo = it.episodeNo,
        episodeUrl = it.episodeUrl,
        episodeId = it.episodeId,
        subOrDub = it.subOrDub
    )
}

fun List<PopularAnimeModelItemDTO>.toDomainPopularItem() : List<PopularAnimeModelItem> = map {
    PopularAnimeModelItem(
        animeUrl = it.animeUrl,
        id = it.id,
        img = it.img,
        name = it.name,
        releasedYear =  it.releasedYear
    )
}

fun List<AnimeMovieItemDTO>.toDomainAnimeMovieItem() : List<AnimeMovieItem> = map {
    AnimeMovieItem(
        animeUrl = it.animeUrl,
        id = it.id,
        img = it.img,
        name = it.name,
        releasedYear =  it.releasedYear
    )
}