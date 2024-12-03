package com.generativeai.aniverse.presentation.stateHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlatformStateHandler {
    private val stateData = mutableMapOf<String, String?>()
    private val stateFlows = mutableMapOf<String, MutableStateFlow<String?>>()

    operator fun set(key: String, value: String) {
        stateData[key] = value
        stateFlows[key]?.value = value
    }

    @Suppress("UNCHECKED_CAST")
    fun get(key: String): String? {
        return stateData[key]
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getStateFlow(key: String, initialValue: String): StateFlow<String> {
        val existingFlow = stateFlows[key]
        if (existingFlow != null) {
            return existingFlow as StateFlow<String>
        }

        val newFlow = MutableStateFlow(initialValue)
        stateFlows[key] = newFlow as MutableStateFlow<String?>
        return newFlow
    }
}

