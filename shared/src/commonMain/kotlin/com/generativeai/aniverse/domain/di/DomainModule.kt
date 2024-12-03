package com.generativeai.aniverse.domain.di

import com.generativeai.aniverse.domain.usecases.GetPopularAnimeUseCase
import com.generativeai.aniverse.domain.usecases.GetAnimeHomeUseCase
import com.generativeai.aniverse.domain.usecases.GetAnimeMovieUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetAnimeHomeUseCase(get()) }
    factory { GetPopularAnimeUseCase(get()) }
    factory { GetAnimeMovieUseCase(get()) }
}