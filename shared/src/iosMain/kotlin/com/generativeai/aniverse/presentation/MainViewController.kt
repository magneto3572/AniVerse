package com.generativeai.aniverse.presentation

import androidx.compose.ui.window.ComposeUIViewController
import com.generativeai.aniverse.presentation.di.ProvideViewModel
import com.generativeai.aniverse.presentation.ui.home.HomeScreen


fun MainViewController() = ComposeUIViewController {
    val viewmodel = ProvideViewModel.getAnimeViewModel()
    HomeScreen(viewModel = viewmodel)
}