package com.generativeai.aniverse.presentation.di

import com.generativeai.aniverse.presentation.viewmodel.AnimeViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module

private val viewModelModules = module {
    single { AnimeViewModel(get(), get(), get(), get(), get()) }
}

actual fun presentationModule(): Module  = viewModelModules

object ProvideViewModel : KoinComponent {
    fun getAnimeViewModel() = get<AnimeViewModel>()
}