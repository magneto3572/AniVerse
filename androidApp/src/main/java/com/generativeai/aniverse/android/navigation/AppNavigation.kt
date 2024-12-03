package com.generativeai.aniverse.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.generativeai.aniverse.android.ui.home.HomeScreen
import com.generativeai.aniverse.presentation.viewmodel.AnimeViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
sealed class Dest {
    @Serializable
    data object Home : Dest()
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = Dest.Home){

        composable<Dest.Home> {
            val viewmodel = koinViewModel<AnimeViewModel>()
            HomeScreen(viewModel = viewmodel)
        }
    }
}