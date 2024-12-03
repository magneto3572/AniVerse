package com.generativeai.aniverse.domain.usecases

import com.generativeai.aniverse.domain.model.AnimeMovieItem
import com.generativeai.aniverse.domain.repository.AnimeRepository

class GetAnimeMovieUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(): Result<List<AnimeMovieItem>> {
        return runCatching {
            val response = repository.getAnimeMovieList()
            Result.success(response)
        }.getOrElse {
            it.printStackTrace()
            Result.failure(it)
        }
    }
}