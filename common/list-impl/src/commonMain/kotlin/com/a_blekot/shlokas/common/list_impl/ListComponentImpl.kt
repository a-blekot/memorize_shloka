package com.a_blekot.shlokas.common.list_impl

import com.a_blekot.shlokas.common.data.PlayConfig
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.utils.Consumer
import com.a_blekot.shlokas.common.utils.asValue
import com.a_blekot.shlokas.common.utils.getStore

import com.a_blekot.shlokas.common.list_api.ListComponent
import com.a_blekot.shlokas.common.list_api.ListOutput
import com.a_blekot.shlokas.common.list_api.ListState
import com.a_blekot.shlokas.common.list_impl.store.ListIntent
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.*
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.a_blekot.shlokas.common.list_impl.store.ListStoreFactory
import com.a_blekot.shlokas.common.utils.getCurrentWeek
import com.a_blekot.shlokas.common.utils.getPause
import com.a_blekot.shlokas.common.utils.getRepeats

class ListComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    deps: ListDeps,
    private val output: Consumer<ListOutput>
) : ListComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            ListStoreFactory(
                storeFactory = storeFactory,
                deps = deps,
            ).create()
        }

    override val flow: Value<ListState> = store.asValue()

    override fun add() = store.accept(Add)
    override fun save() = store.accept(Save)
    override fun remove(id: String) = store.accept(Remove(id))
    override fun moveUp(id: String) = store.accept(MoveUp(id))
    override fun moveDown(id: String) = store.accept(MoveDown(id))
    override fun select(id: String, isSelected: Boolean) = store.accept(Select(id, isSelected))
    override fun details(config: ShlokaConfig) = output(ListOutput.Details(config))
    override fun play() = output(ListOutput.Play(store.state.toPlayConfig()))
    override fun play(config: ShlokaConfig) = output(ListOutput.Play(config.toPlayConfig()))
    override fun settings() = output(ListOutput.Settings)
    override fun saveShloka(config: ShlokaConfig) = store.accept(SaveShloka(config))

    private fun ListState.toPlayConfig() =
        PlayConfig(
            getCurrentWeek(),
            config.list,
            getRepeats(),
            getPause()
        )

    private fun ShlokaConfig.toPlayConfig() =
        PlayConfig(
            getCurrentWeek(),
            listOf(copy(isSelected = true)),
            getRepeats(),
            getPause(),
        )
}