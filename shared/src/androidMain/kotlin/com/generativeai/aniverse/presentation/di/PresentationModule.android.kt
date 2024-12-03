package com.generativeai.aniverse.presentation.di

import com.generativeai.aniverse.presentation.viewmodel.AnimeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel { AnimeViewModel(get(), get(), get(), get(), get()) }
}

actual fun presentationModule(): Module = viewModelModule