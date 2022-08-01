package com.a_blekot.shlokas.common.list_impl.store

import com.a_blekot.shlokas.common.data.ListConfig
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.list_api.ListState
import com.a_blekot.shlokas.common.list_impl.ListDeps
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.Add
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.MoveDown
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.MoveUp
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.Remove
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.Save
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.SaveShloka
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.Title
import com.a_blekot.shlokas.common.list_impl.store.ListStoreFactory.Action.LoadLastConfig
import com.a_blekot.shlokas.common.utils.getLastListFileName
import com.a_blekot.shlokas.common.utils.readFromFile
import com.a_blekot.shlokas.common.utils.saveLastListFileName
import com.a_blekot.shlokas.common.utils.writeToFile
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch

internal class ListStoreFactory(
    private val storeFactory: StoreFactory,
    private val deps: ListDeps
) {

    fun create(): ListStore =
        object : ListStore, Store<ListIntent, ListState, Nothing> by storeFactory.create(
            name = "ListStore",
            initialState = ListState(deps.config),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl()
        ) {}

    sealed interface Action {
        object LoadLastConfig: Action
    }

    sealed interface Msg {
        object AddShloka : Msg
        data class Update(val config: ListConfig) : Msg
        data class Title(val title: String) : Msg
        data class RemoveShloka(val id: Int) : Msg
        data class MoveUp(val id: Int) : Msg
        data class MoveDown(val id: Int) : Msg
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
                    val lastPath = getLastListFileName()
                    val config = readFromFile(lastPath, deps.filer)
                    config?.let {
                        deps.config = config
                        dispatch(Msg.Update(config))
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
                is SaveShloka -> saveShloka(getState().config, intent.config)
            }
        }

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
            }

        private fun update(newConfig: ListConfig): ListState {
            saveLastListFileName(newConfig.fileName)
            return ListState(newConfig, hasChanges = newConfig != deps.config)
        }

        private fun ListConfig.add(): ListConfig =
            copy(list = list.toMutableList().apply { add(0, ShlokaConfig()) })

        private fun ListConfig.remove(id: Int): ListConfig =
            copy(list = list.toMutableList().apply { removeAll { it.shloka.id == id }})

        private fun ListConfig.moveUp(id: Int): ListConfig =
            copy(list = list.toMutableList().apply {
                val index = indexOfFirst { it.shloka.id == id }
                if (index > 0) {
                    val shloka = removeAt(index)
                    add(index - 1, shloka)
                }
            })

        private fun ListConfig.moveDown(id: Int): ListConfig =
            copy(list = list.toMutableList().apply {
                val index = indexOfFirst { it.shloka.id == id }
                if (index < lastIndex) {
                    val shloka = removeAt(index)
                    add(index + 1, shloka)
                }
            })
    }
}
