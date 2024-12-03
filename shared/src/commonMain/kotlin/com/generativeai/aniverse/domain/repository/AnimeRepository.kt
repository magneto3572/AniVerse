package com.generativeai.aniverse.domain.repository

import com.generativeai.aniverse.domain.model.AnimeMovieItem
import com.generativeai.aniverse.domain.model.AnimeResponse
import com.generativeai.aniverse.domain.model.PopularAnimeModelItem

interface AnimeRepository {
    suspend fun getAnimeHome() : AnimeResponse
    suspend fun getPopularAnimeList() : List<PopularAnimeModelItem>
    suspend fun getAnimeMovieList() : List<AnimeMovieItem>
}