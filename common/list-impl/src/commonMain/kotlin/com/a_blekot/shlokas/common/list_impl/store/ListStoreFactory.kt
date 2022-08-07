package com.a_blekot.shlokas.common.list_impl.store

import com.a_blekot.shlokas.common.data.ListConfig
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.list_api.ListState
import com.a_blekot.shlokas.common.list_impl.ListDeps
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.*
import com.a_blekot.shlokas.common.list_impl.store.ListStoreFactory.Action.LoadLastConfig
import com.a_blekot.shlokas.common.utils.*
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch

internal class ListStoreFactory(
    private val storeFactory: StoreFactory,
    private val deps: ListDeps
) : StringResourceHandler by deps.stringResourceHandler {

    fun create(): ListStore =
        object : ListStore, Store<ListIntent, ListState, Nothing> by storeFactory.create(
            name = "ListStore",
            initialState = ListState(deps.config),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl()
        ) {}

    sealed interface Action {
        object LoadLastConfig : Action
    }

    sealed interface Msg {
        object AddShloka : Msg
        data class Update(val config: ListConfig) : Msg
        data class Title(val title: String) : Msg
        data class RemoveShloka(val id: String) : Msg
        data class MoveUp(val id: String) : Msg
        data class MoveDown(val id: String) : Msg
        data class Select(val id: String, val isSelected: Boolean) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(LoadLastConfig)
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<ListIntent, Action, ListState, Msg, Nothing>() {
        override fun executeAction(action: Action, getState: () -> ListState) {
            when (action) {
                LoadLastConfig -> {
                    val id = getLastConfigId().ifBlank { "sb_1_canto_config" }
                    val config = readFirstCanto(id, deps.filer, deps.configReader)

                    config?.let {
                        deps.config = config.updateTitles()
                        dispatch(Msg.Update(deps.config))
                    }
                }
            }
        }

        override fun executeIntent(intent: ListIntent, getState: () -> ListState) {
            when (intent) {
                Add -> dispatch(Msg.AddShloka)
                Save -> saveList(getState().config)
                is Title -> rename(getState().config.title, intent.title)
                is Remove -> dispatch(Msg.RemoveShloka(intent.id))
                is MoveUp -> dispatch(Msg.MoveUp(intent.id))
                is MoveDown -> dispatch(Msg.MoveDown(intent.id))
                is Select -> dispatch(Msg.Select(intent.id, intent.isSelected))
                is SaveShloka -> saveShloka(getState().config, intent.config)
            }
        }

        private fun ListConfig.updateTitles() =
            copy(
                title = resolveListTitle(id),
                list = list.map {
                    it.copy(shloka = it.shloka.copy(title = resolveTitle(it.shloka.id)))
                }
            )

        private fun saveShloka(listConfig: ListConfig, shlokaConfig: ShlokaConfig) =
            saveList(listConfig.save(shlokaConfig))

        private fun saveList(config: ListConfig) {
            if (writeToFile(config, deps.filer)) {
                deps.config = config
                dispatch(Msg.Update(config))
            }
        }

        private fun rename(old: String, new: String) {
            if (deps.filer.rename(old, new)) {
                dispatch(Msg.Title(new))
            }
        }

        private fun ListConfig.save(config: ShlokaConfig): ListConfig =
            copy(list = list.toMutableList().apply {
                val index = indexOfFirst { it.shloka.id == config.shloka.id }
                if (index == -1) return@apply

                set(index, config)
            })
    }

    private inner class ReducerImpl : Reducer<ListState, Msg> {
        override fun ListState.reduce(msg: Msg): ListState =
            when (msg) {
                Msg.AddShloka -> update(newConfig = config.add())
                is Msg.Update -> update(newConfig = msg.config)
                is Msg.Title -> update(newConfig = config.copy(title = msg.title))
                is Msg.RemoveShloka -> update(newConfig = config.remove(msg.id))
                is Msg.MoveUp -> update(newConfig = config.moveUp(msg.id))
                is Msg.MoveDown -> update(newConfig = config.moveDown(msg.id))
                is Msg.Select -> update(newConfig = config.select(msg.id, msg.isSelected))
            }

        private fun update(newConfig: ListConfig): ListState {
            saveLastConfigId(newConfig.id)
            return ListState(newConfig, hasChanges = newConfig != deps.config)
        }

        private fun ListConfig.add(): ListConfig =
            copy(list = list.toMutableList().apply { add(0, ShlokaConfig()) })

        private fun ListConfig.remove(id: String): ListConfig =
            copy(list = list.toMutableList().apply { removeAll { it.shloka.id == id } })

        private fun ListConfig.moveUp(id: String): ListConfig =
            copy(list = list.toMutableList().apply {
                val index = indexOfFirst { it.shloka.id == id }
                if (index > 0) {
                    val shloka = removeAt(index)
                    add(index - 1, shloka)
                }
            })

        private fun ListConfig.moveDown(id: String): ListConfig =
            copy(list = list.toMutableList().apply {
                val index = indexOfFirst { it.shloka.id == id }
                if (index < lastIndex) {
                    val shloka = removeAt(index)
                    add(index + 1, shloka)
                }
            })

        private fun ListConfig.select(id: String, isSelected: Boolean): ListConfig =
            copy(list = list.toMutableList().apply {
                val index = indexOfFirst { it.shloka.id == id }
                if (index >= 0) {
                    val shloka = get(index)
                    set(index, shloka.copy(isSelected = isSelected))
                }
            })
    }
}
