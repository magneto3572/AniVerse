package com.generativeai.aniverse.domain.usecases

import com.generativeai.aniverse.domain.model.AnimeResponse
import com.generativeai.aniverse.domain.repository.AnimeRepository

class GetAnimeHomeUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(): Result<AnimeResponse> {
        return runCatching {
            Result.success(repository.getAnimeHome())
        }.getOrElse {
            it.printStackTrace()
            Result.failure(it)
        }
    }
}