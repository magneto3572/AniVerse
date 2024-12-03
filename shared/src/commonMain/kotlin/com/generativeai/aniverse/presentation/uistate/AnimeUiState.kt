package com.generativeai.aniverse.presentation.uistate

import androidx.compose.runtime.Immutable
import com.generativeai.aniverse.data.dto.OnGoingSeryDTO
import com.generativeai.aniverse.data.dto.RecentReleaseDTO
import com.generativeai.aniverse.data.dto.RecentyAddedSeryDTO
import com.generativeai.aniverse.domain.model.AnimeMovieItem
import com.generativeai.aniverse.domain.model.AnimeResponse
import com.generativeai.aniverse.domain.model.PopularAnimeModelItem
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class AnimeUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val animeHome : AnimeResponse? = AnimeResponse(genres = emptyList(), onGoingSeries = emptyList(), recentReleases = emptyList(), recentlyAddedSeries = emptyList()),
    val animeMovieItemList : List<AnimeMovieItem>? = emptyList(),
    val popularAnimeItemList: List<PopularAnimeModelItem>? = emptyList(),
    val errorMessage: String? = null
) {
    @Serializable
    sealed class PartialState {
        data object Loading : PartialState()
        data class Update(val animeHome: AnimeResponse?, val animeMovieItemList: List<AnimeMovieItem>?, val popularAnimeItemList: List<PopularAnimeModelItem>?) : PartialState()
        data class Error(val errorMessage: String?) : PartialState()
    }
}

