package com.generativeai.aniverse.presentation.ui.home

import AutoSlidingSnapCarousel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.generativeai.aniverse.domain.model.RecentRelease
import com.generativeai.aniverse.presentation.intent.AnimeScreenIntent
import com.generativeai.aniverse.presentation.ui.utils.getItemImage
import com.generativeai.aniverse.presentation.ui.utils.getItemTitle
import com.generativeai.aniverse.presentation.uistate.AnimeUiState
import com.generativeai.aniverse.presentation.viewmodel.AnimeViewModel


@Composable
fun HomeScreen(
    viewModel: AnimeViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreenContent(
        animeUiState = uiState,
        animScreenIntent = viewModel::acceptIntent,
    )
}

@Composable
private fun MainScreenContent(
    animeUiState: AnimeUiState,
    animScreenIntent: (AnimeScreenIntent) -> Unit,
) {
    if(animeUiState.isLoading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            CircularProgressIndicator(
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center) // Correct alignment
            )
        }
    }

    if (animeUiState.isError){
        return
    }

    if (animeUiState.animeHome?.recentReleases?.isNotEmpty() == true){
        MainContent(animeUiState)
    }
}
@Composable
fun MainContent(
    animeUiState: AnimeUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Header Section
        animeUiState.animeHome?.recentReleases?.let { TopHeaderSection(it) }

        // OnGoingList
        Section(
            title = "OnGoing Series",
            itemList = animeUiState.animeHome?.onGoingSeries,
            cardWidth = 165.dp,
            cardHeight = 180.dp
        )

        // Popular Anime
        Section(
            title = "Popular Anime",
            itemList = animeUiState.popularAnimeItemList,
            cardWidth = 300.dp,
            cardHeight = 180.dp
        )

        // Recent Release
        Section(
            title = "Recently Added",
            itemList = animeUiState.animeHome?.recentlyAddedSeries,
            cardWidth = 165.dp,
            cardHeight = 180.dp
        )

        // Anime Movie
        Section(
            title = "Anime Movie",
            itemList = animeUiState.animeMovieItemList,
            cardWidth = 165.dp,
            cardHeight = 180.dp
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun <T : Any> Section(
    title: String,
    itemList: List<T>?,
    cardWidth: Dp,
    cardHeight: Dp,
) {
    itemList?.let {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            fontSize = 16.sp,
            color = Color.White
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(it) { item ->
                AnimeCard(
                    imageUrl = getItemImage(item),
                    title = getItemTitle(item),
                    cardWidth = cardWidth,
                    cardHeight = cardHeight
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AnimeCard(
    imageUrl: String,
    title: String,
    cardWidth: Dp,
    cardHeight: Dp
) {
    Box(
        modifier = Modifier
            .width(cardWidth)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
    ) {
        // Image as background
        AsyncImage(
            model = imageUrl,
            onLoading = {

            },
            onError = {
                println("Something went wrong: ${it.result.throwable}")
            },
            onSuccess = {
                println("Image Loaded Successfully")
            },
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
        )

        // Title overlay
        Text(
            text = title,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier
                .heightIn(min = 45.dp, max = 45.dp)
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black
                        )
                    )
                )
                .padding(vertical = 12.dp, horizontal = 8.dp)
        )
    }
}

@Composable
fun TopHeaderSection(
    list: List<RecentRelease>
) {
    AutoSlidingSnapCarousel(list)
}

