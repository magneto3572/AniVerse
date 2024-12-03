package com.generativeai.aniverse.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.generativeai.aniverse.presentation.stateHandler.PlatformStateHandler
import com.generativeai.aniverse.domain.usecases.GetPopularAnimeUseCase
import com.generativeai.aniverse.domain.usecases.GetAnimeHomeUseCase
import com.generativeai.aniverse.domain.usecases.GetAnimeMovieUseCase
import com.generativeai.aniverse.presentation.intent.AnimeScreenIntent
import com.generativeai.aniverse.presentation.uistate.AnimeUiState
import com.generativeai.aniverse.presentation.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class AnimeViewModel(
    private val getAnimeMovieUseCase: GetAnimeMovieUseCase,
    private val getAnimeHomeUseCase: GetAnimeHomeUseCase,
    private val getPopularAnimeUseCase: GetPopularAnimeUseCase,
    animeUiState: AnimeUiState,
    platformStateHandler: PlatformStateHandler
) : BaseViewModel<AnimeUiState, AnimeUiState.PartialState, AnimeScreenIntent>(platformStateHandler, animeUiState, AnimeUiState.serializer()) {

    enum class Fetch {
        ANIME_HOME,
        ANIME_MOVIE,
        POPULAR_ANIME,
        FETCH_ALL
    }

    init {
        acceptIntent(AnimeScreenIntent.FetchAll)
    }

    override fun mapIntents(intent: AnimeScreenIntent): Flow<AnimeUiState.PartialState> {
        return when (intent) {
            is AnimeScreenIntent.FetchAnimeHome -> {
                fetchData(Fetch.ANIME_HOME)
            }
            is AnimeScreenIntent.FetchAnimeMovie -> {
                fetchData(Fetch.ANIME_MOVIE)
            }
            is AnimeScreenIntent.FetchPopularAnime -> {
                fetchData(Fetch.POPULAR_ANIME)
            }
            is AnimeScreenIntent.FetchAll -> {
                fetchData(Fetch.FETCH_ALL)
            }
        }
    }

    private fun fetchData(type: Fetch): Flow<AnimeUiState.PartialState> = flow {
        when (type) {
            Fetch.ANIME_HOME -> {
                val response = getAnimeHomeUseCase.invoke()
                handleResponse(response){ animeHomeRes ->
                    AnimeUiState.PartialState.Update(
                        animeMovieItemList = uiState.value.animeMovieItemList,
                        animeHome = animeHomeRes,
                        popularAnimeItemList = uiState.value.popularAnimeItemList
                    )
                }
            }
            Fetch.ANIME_MOVIE -> {
                val response = getAnimeMovieUseCase.invoke()
                handleResponse(response){ animeMovieRes ->
                    AnimeUiState.PartialState.Update(
                        animeMovieItemList = animeMovieRes,
                        animeHome = uiState.value.animeHome,
                        popularAnimeItemList = uiState.value.popularAnimeItemList
                    )
                }
            }
            Fetch.POPULAR_ANIME -> {
                val response = getPopularAnimeUseCase.invoke()
                handleResponse(response){ popularAnime ->
                    AnimeUiState.PartialState.Update(
                        animeMovieItemList = uiState.value.animeMovieItemList,
                        animeHome = uiState.value.animeHome,
                        popularAnimeItemList = popularAnime
                    )
                }
            }

            Fetch.FETCH_ALL -> {
                val animeHomeFlow = flow { emit(getAnimeHomeUseCase.invoke()) }
                val animeMovieFlow = flow { emit(getAnimeMovieUseCase.invoke()) }
                val popularAnimeFlow = flow { emit(getPopularAnimeUseCase.invoke()) }

                combine(animeHomeFlow, animeMovieFlow, popularAnimeFlow) { animeHomeRes, animeMovieRes, popularAnimeRes ->
                    Triple(animeHomeRes, animeMovieRes, popularAnimeRes)
                }.collect { (animeHomeRes, animeMovieRes, popularAnimeRes) ->
                    println(popularAnimeRes.toString())
                    val newState = AnimeUiState.PartialState.Update(
                        animeMovieItemList = animeMovieRes.getOrNull(),
                        animeHome = animeHomeRes.getOrNull(),
                        popularAnimeItemList = popularAnimeRes.getOrNull()
                    )
                    emit(newState)
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend inline fun <T> FlowCollector<AnimeUiState.PartialState>.handleResponse(response: Result<T>,
        crossinline onSuccess: (T) -> AnimeUiState.PartialState.Update
    ) {
        response.fold(
            onSuccess = { res -> emit(onSuccess(res)) },
            onFailure = { throwable -> emit(AnimeUiState.PartialState.Error(throwable.message)) }
        )
    }

    override fun reduceUiState(
        previousState: AnimeUiState,
        partialState: AnimeUiState.PartialState
    ): AnimeUiState = when (partialState) {

        is AnimeUiState.PartialState.Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )

        is AnimeUiState.PartialState.Update -> previousState.copy(
            isLoading = false,
            animeHome = partialState.animeHome,
            animeMovieItemList = partialState.animeMovieItemList,
            popularAnimeItemList = partialState.popularAnimeItemList,
            isError = false,
        )

        is AnimeUiState.PartialState.Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
    }
}