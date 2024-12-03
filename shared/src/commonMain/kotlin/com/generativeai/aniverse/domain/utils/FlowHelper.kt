package com.generativeai.aniverse.domain.utils


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal fun <T:Any> StateFlow<T>.common()= CommonStateFlow<T>(this)

class CommonCancellable<T>(val onCancel: () -> Unit)

class CommonStateFlow<T>(
    val flow: StateFlow<T>
) : StateFlow<T> {

    override val replayCache: List<T>
        get() = flow.replayCache
    override val value: T
        get() = flow.value

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        flow.collect(collector)
    }

    fun startCollection(onEach: (T) -> Unit, onCancel: () -> Unit): CommonCancellable<T> {
        val scope = CoroutineScope(Dispatchers.Main)
        val job = scope.launch {
            try {
                collect {
                    onEach.invoke(it)
                }
            } catch (e: Exception) {
                onCancel.invoke()
            }
        }
        return CommonCancellable { job.cancel() }
    }
}