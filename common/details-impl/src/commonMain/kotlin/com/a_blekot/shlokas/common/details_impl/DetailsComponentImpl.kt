package com.a_blekot.shlokas.common.details_impl

import com.a_blekot.shlokas.common.utils.Consumer
import com.a_blekot.shlokas.common.utils.asValue
import com.a_blekot.shlokas.common.utils.getStore

import com.a_blekot.shlokas.common.details_api.DetailsComponent
import com.a_blekot.shlokas.common.details_api.DetailsOutput
import com.a_blekot.shlokas.common.details_api.DetailsState
import com.a_blekot.shlokas.common.details_impl.store.DetailsIntent.Title
import com.a_blekot.shlokas.common.details_impl.store.DetailsIntent.FilePath
import com.a_blekot.shlokas.common.details_impl.store.DetailsIntent.Description
import com.a_blekot.shlokas.common.details_impl.store.DetailsIntent.ChunkStart
import com.a_blekot.shlokas.common.details_impl.store.DetailsIntent.ChunkEnd
import com.a_blekot.shlokas.common.details_impl.store.DetailsIntent.Pause
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.a_blekot.shlokas.common.details_impl.store.DetailsStoreFactory

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
    override fun setTitle(title: String) = store.accept(Title(title))
    override fun setFilePath(filePath: String) = store.accept(FilePath(filePath))
    override fun setDescription(description: String) = store.accept(Description(description))
    override fun setChunkStart(index: Int, startMs: Long) = store.accept(ChunkStart(index, startMs))
    override fun setChunkEnd(index: Int, endMs: Long) = store.accept(ChunkEnd(index, endMs))
    override fun setPause(pause: Long) = store.accept(Pause(pause))
}