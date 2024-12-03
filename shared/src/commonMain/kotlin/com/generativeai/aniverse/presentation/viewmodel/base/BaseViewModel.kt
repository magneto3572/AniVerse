package com.generativeai.aniverse.presentation.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.generativeai.aniverse.domain.constants.SAVED_UI_STATE_KEY
import com.generativeai.aniverse.presentation.stateHandler.PlatformStateHandler
import com.generativeai.aniverse.domain.utils.common
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseViewModel<UI_STATE : Any, PARTIAL_UI_STATE, INTENT>(
    private val platformStateHandler: PlatformStateHandler,
    initialState: UI_STATE,
    private val uiStateSerializer: KSerializer<UI_STATE>
) : ViewModel() {

    private val intentFlow = MutableSharedFlow<INTENT>()


    private val initialUiState: UI_STATE = platformStateHandler.get(SAVED_UI_STATE_KEY)?.let { jsonString ->
        try {
            Json.decodeFromString(uiStateSerializer, jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            initialState
        }
    } ?: initialState

    // Define uiState as a StateFlow, starting with the initialUiState
    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<UI_STATE> get() = _uiState.asStateFlow().common()

    init {
        viewModelScope.launch {
            intentFlow
                .flatMapMerge { intent ->
                    mapIntents(intent)
                }
                .scan(_uiState.value) { previousState, partialState ->
                    reduceUiState(previousState, partialState)
                }
                .catch { e ->
                    e.printStackTrace()
                }
                .collect { newState ->
                    _uiState.value = newState

                    // Serialize newState to JSON and save it in SavedStateHandle
                    try {
                        val jsonString = Json.encodeToString(uiStateSerializer, newState)
                        println("json data ->>>>>>>  $jsonString")
                        platformStateHandler[SAVED_UI_STATE_KEY] = jsonString
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        }
    }

    fun acceptIntent(intent: INTENT) {
        viewModelScope.launch {
            intentFlow.emit(intent)
        }
    }

    protected abstract fun mapIntents(intent: INTENT): Flow<PARTIAL_UI_STATE>

    protected abstract fun reduceUiState(
        previousState: UI_STATE,
        partialState: PARTIAL_UI_STATE,
    ): UI_STATE
}
