package com.generativeai.aniverse.data.di

import com.generativeai.aniverse.data.remote.ApiService
import com.generativeai.aniverse.data.remote.KtorClient
import com.generativeai.aniverse.data.repositoryImpl.AnimeRepositoryImpl
import com.generativeai.aniverse.domain.repository.AnimeRepository
import org.koin.dsl.module

val dataModule = module {
    factory {ApiService(KtorClient.client)}
    factory<AnimeRepository> { AnimeRepositoryImpl(apiService = get()) }
}