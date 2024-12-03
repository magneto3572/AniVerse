package com.generativeai.aniverse.data.repositoryImpl

import com.generativeai.aniverse.data.mappers.toDomainAnimeMovieItem
import com.generativeai.aniverse.data.mappers.toDomainAnimeResponse
import com.generativeai.aniverse.data.mappers.toDomainPopularItem
import com.generativeai.aniverse.data.remote.ApiService
import com.generativeai.aniverse.domain.repository.AnimeRepository

class AnimeRepositoryImpl(private val apiService: ApiService) : AnimeRepository {

    override suspend fun getAnimeHome() =  apiService.getAnimeList().toDomainAnimeResponse()

    override suspend fun getPopularAnimeList() = apiService.getPopularAnime().toDomainPopularItem()

    override suspend fun getAnimeMovieList() = apiService.getAnimeMovie().toDomainAnimeMovieItem()
}