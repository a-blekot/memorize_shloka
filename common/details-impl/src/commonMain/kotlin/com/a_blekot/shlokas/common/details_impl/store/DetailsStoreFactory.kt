package com.a_blekot.shlokas.common.details_impl.store

import com.a_blekot.shlokas.common.data.Chunk
import com.a_blekot.shlokas.common.data.Shloka
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.details_api.DetailsState
import com.a_blekot.shlokas.common.details_impl.DetailsDeps
import com.a_blekot.shlokas.common.details_impl.store.DetailsIntent.*
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

internal class DetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val deps: DetailsDeps
) {

    fun create(): DetailsStore =
        object : DetailsStore, Store<DetailsIntent, DetailsState, Nothing> by storeFactory.create(
            name = "DetailsStore",
            initialState = DetailsState(deps.config),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl()
        ) {}


    sealed interface Msg {
        data class TitleChanged(val value: String) : Msg
        data class FilePathChanged(val value: String) : Msg
        data class SanskritChanged(val value: String) : Msg
        data class WordsChanged(val value: String) : Msg
        data class TranslationChanged(val value: String) : Msg
        data class ChunkStartChanged(val index: Int, val value: Long) : Msg
        data class ChunkEndChanged(val index: Int, val value: Long) : Msg
        data class PauseChanged(val value: Long) : Msg
        data class IsSelectedChanged(val value: Boolean) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<DetailsIntent, Nothing, DetailsState, Msg, Nothing>() {
        override fun executeIntent(intent: DetailsIntent, getState: () -> DetailsState) {
            when (intent) {
                is Title -> dispatch(Msg.TitleChanged(intent.value))
                is FilePath -> dispatch(Msg.FilePathChanged(intent.value))
                is Sanskrit -> dispatch(Msg.SanskritChanged(intent.value))
                is Words -> dispatch(Msg.WordsChanged(intent.value))
                is Translation -> dispatch(Msg.TranslationChanged(intent.value))
                is ChunkStart -> dispatch(Msg.ChunkStartChanged(intent.index, intent.value))
                is ChunkEnd -> dispatch(Msg.ChunkEndChanged(intent.index, intent.value))
                is Pause -> dispatch(Msg.PauseChanged(intent.value))
                is IsSelected -> dispatch(Msg.IsSelectedChanged(intent.isSelected))
            }
        }
    }

    private inner class ReducerImpl : Reducer<DetailsState, Msg> {
        override fun DetailsState.reduce(msg: Msg): DetailsState =
            when (msg) {
                is Msg.TitleChanged -> update(newConfig = config.update { copy(title = msg.value) })
                is Msg.FilePathChanged -> {this} //update(newConfig = config.update { copy(fileName = msg.value) })
                is Msg.SanskritChanged -> update(newConfig = config.update { copy(sanskrit = msg.value) })
                is Msg.WordsChanged -> update(newConfig = config.update { copy(words = msg.value) })
                is Msg.TranslationChanged -> update(newConfig = config.update { copy(translation = msg.value) })
                is Msg.ChunkStartChanged -> update(newConfig = config.update(msg.index) { copy(startMs = msg.value) })
                is Msg.ChunkEndChanged -> update(newConfig = config.update(msg.index) { copy(endMs = msg.value) })
                is Msg.PauseChanged -> update(newConfig = config.copy(pauseAfterEach = msg.value))
                is Msg.IsSelectedChanged -> update(newConfig = config.copy(isSelected = msg.value))
            }

        private fun update(newConfig: ShlokaConfig): DetailsState =
            DetailsState(newConfig, hasChanges = newConfig != deps.config)

        private fun ShlokaConfig.update(update: Shloka.() -> Shloka) =
            copy(shloka = shloka.update())

        private fun ShlokaConfig.update(index: Int, update: Chunk.() -> Chunk): ShlokaConfig {
            val newChunks = chunks.toMutableList()
            newChunks[index] = newChunks[index].update()

            return copy(chunks = newChunks)
        }
    }
}
