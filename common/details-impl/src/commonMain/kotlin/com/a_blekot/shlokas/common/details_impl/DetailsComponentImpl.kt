package com.a_blekot.shlokas.common.details_impl

import com.a_blekot.shlokas.common.utils.Consumer
import com.a_blekot.shlokas.common.utils.asValue
import com.a_blekot.shlokas.common.utils.getStore

import com.a_blekot.shlokas.common.details_api.DetailsComponent
import com.a_blekot.shlokas.common.details_api.DetailsOutput
import com.a_blekot.shlokas.common.details_api.DetailsState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.listentoprabhupada.common.favorites_impl.store.DetailsStoreFactory

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

    override fun saveChanges() = output(DetailsOutput.SaveChanges)
    override fun setTitle(title: String)
    override fun setFilePath(filePath: String)
    override fun setDescription(description: String)
    override fun setChunkStart(index: Int, startMs: Long)
    override fun setChunkEnd(index: Int, endMs: Long)
    override fun setPause(pause: Long) = store.accept(CurrentLecture(id, isPlaying))
}