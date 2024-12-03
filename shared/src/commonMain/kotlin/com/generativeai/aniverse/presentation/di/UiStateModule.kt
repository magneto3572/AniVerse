package com.generativeai.aniverse.presentation.di

import com.generativeai.aniverse.presentation.stateHandler.PlatformStateHandler
import com.generativeai.aniverse.presentation.uistate.AnimeUiState
import org.koin.dsl.module

val uiStateModule = module {
    factory { AnimeUiState() }
    factory { PlatformStateHandler() }
}