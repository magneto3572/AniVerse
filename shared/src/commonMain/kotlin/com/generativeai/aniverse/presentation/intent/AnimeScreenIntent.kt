package com.generativeai.aniverse.presentation.intent


sealed class AnimeScreenIntent {
    data object FetchAnimeHome : AnimeScreenIntent()
    data object FetchAnimeMovie : AnimeScreenIntent()
    data object FetchPopularAnime : AnimeScreenIntent()
    data object FetchAll : AnimeScreenIntent()
}
