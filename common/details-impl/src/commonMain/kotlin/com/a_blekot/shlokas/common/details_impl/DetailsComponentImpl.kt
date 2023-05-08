package com.a_blekot.shlokas.common.details_impl

import com.a_blekot.shlokas.common.data.PlayConfig

import com.a_blekot.shlokas.common.details_api.DetailsComponent
import com.a_blekot.shlokas.common.details_api.DetailsOutput
import com.a_blekot.shlokas.common.details_api.DetailsState
import com.a_blekot.shlokas.common.details_impl.store.DetailsIntent.*
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.a_blekot.shlokas.common.details_impl.store.DetailsStoreFactory
import com.a_blekot.shlokas.common.utils.*

class DetailsComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    deps: DetailsDeps,
    private val output: Consumer<DetailsOutput>
) : DetailsComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            DetailsStoreFactory(
                storeFactory = storeFactory,
                deps = deps,
            ).create()
        }

    override val flow: Value<DetailsState> = store.asValue()

    override fun saveChanges() = output(DetailsOutput.SaveConfig(store.state.config))
    override fun play() = output(DetailsOutput.Play(store.state.toPlayConfig()))
    override fun setTitle(title: String) = store.accept(Title(title))
    override fun setFilePath(filePath: String) = store.accept(FilePath(filePath))
    override fun setSanskrit(value: String) = store.accept(Sanskrit(value))
    override fun setWords(value: String) = store.accept(Words(value))
    override fun setTranslation(value: String) = store.accept(Translation(value))
    override fun setChunkStart(index: Int, startMs: Long) = store.accept(ChunkStart(index, startMs))
    override fun setChunkEnd(index: Int, endMs: Long) = store.accept(ChunkEnd(index, endMs))
    override fun setSelected(isSelected: Boolean) = store.accept(IsSelected(isSelected))
    override fun setPause(pause: Long) = store.accept(Pause(pause))

    private fun DetailsState.toPlayConfig() =
        PlayConfig(
            repeatMode = repeatMode,
            shlokas = listOf(config.copy(isSelected = true)),
            repeats = repeats,
            withSanskrit = withSanskrit,
            withTranslation = withTranslation,
            pauseAfterEach = pause,
        )
}