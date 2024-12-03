package com.generativeai.aniverse.domain.usecases

import com.generativeai.aniverse.domain.model.PopularAnimeModelItem
import com.generativeai.aniverse.domain.repository.AnimeRepository

class GetPopularAnimeUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(): Result<List<PopularAnimeModelItem>> {
        return runCatching {
            Result.success(repository.getPopularAnimeList())
        }.getOrElse {
            it.printStackTrace()
            Result.failure(it)
        }
    }
}