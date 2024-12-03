package com.generativeai.aniverse.data.remote

import com.generativeai.aniverse.data.dto.AnimeMovieItemDTO
import com.generativeai.aniverse.data.dto.AnimeResponseDTO
import com.generativeai.aniverse.data.dto.PopularAnimeModelItemDTO
import com.generativeai.aniverse.domain.constants.ANIME_HOME
import com.generativeai.aniverse.domain.constants.ANIME_MOVIE
import com.generativeai.aniverse.domain.constants.ANIME_POPULAR
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class ApiService(private val client : HttpClient) {

    private suspend inline fun <reified T> getResponse(url: String): T {
        return try {
            val response: HttpResponse = client.get(url)
            if (response.status.isSuccess()) {
                response.body()
            } else {
                throw ApiException("Failed to fetch data: ${response.status}")
            }
        } catch (e: Exception) {
            // You can log the exception here or return a default value if needed
            throw ApiException("An error occurred while fetching data: $e")
        }
    }

    suspend fun getAnimeList(): AnimeResponseDTO {
        return getResponse(ANIME_HOME)
    }

    suspend fun getPopularAnime(): List<PopularAnimeModelItemDTO> {
        return getResponse(ANIME_POPULAR)
    }

    suspend fun getAnimeMovie(): List<AnimeMovieItemDTO> {
        return getResponse(ANIME_MOVIE)
    }
}

class ApiException(message: String) : Exception(message)