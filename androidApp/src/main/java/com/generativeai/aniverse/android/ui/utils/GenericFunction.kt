package com.generativeai.aniverse.android.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.generativeai.aniverse.domain.model.AnimeMovieItem
import com.generativeai.aniverse.domain.model.OnGoingSery
import com.generativeai.aniverse.domain.model.PopularAnimeModelItem
import com.generativeai.aniverse.domain.model.RecentRelease
import com.generativeai.aniverse.domain.model.RecentyAddedSery

fun getItemImage(item: Any): String {
    return when (item) {
        is RecentRelease -> item.img
        is RecentyAddedSery -> item.img
        is PopularAnimeModelItem -> item.img
        is AnimeMovieItem -> item.img
        is OnGoingSery -> item.img
        else -> ""
    }
}

fun getItemTitle(item: Any): String {
    return when (item) {
        is RecentRelease -> item.name
        is RecentyAddedSery -> item.name
        is PopularAnimeModelItem -> item.name
        is AnimeMovieItem -> item.name
        is OnGoingSery ->  item.name
        else -> ""
    }
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }
