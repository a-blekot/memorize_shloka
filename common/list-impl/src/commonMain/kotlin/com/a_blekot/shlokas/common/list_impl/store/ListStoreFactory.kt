package com.a_blekot.shlokas.common.list_impl.store

import com.a_blekot.shlokas.common.data.ListConfig
import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.list_api.ListPresentation
import com.a_blekot.shlokas.common.list_api.ListState
import com.a_blekot.shlokas.common.list_impl.ListDeps
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.*
import com.a_blekot.shlokas.common.list_impl.store.ListStoreFactory.Action.LoadLastConfig
import com.a_blekot.shlokas.common.utils.*
import com.a_blekot.shlokas.common.utils.analytics.*
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TUTORIAL_DELAY_MS = 1_000L

internal class ListStoreFactory(
    private val storeFactory: StoreFactory,
    private val deps: ListDeps
) : StringResourceHandler by deps.stringResourceHandler {

    private var locale = getLocale()

    fun create(): ListStore =
        object : ListStore, Store<ListIntent, ListState, ListLabel> by storeFactory.create(
            name = "ListStore",
            autoInit = false,
            initialState = ListState(
                deps.config,
                availableLists = availableLists(),
                locale = getLocale(),
                shouldShowTutorial = false
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl()
        ) {}

    sealed interface Action {
        object LoadLastConfig : Action
    }

    sealed interface Msg {
        object AddShloka : Msg
        object ShowTutorial : Msg
        object TutorialCompleted : Msg
        object TutorialSkipped : Msg
        object ShowPreRating : Msg
        object ClosePreRating : Msg
        data class SetList(val config: ListConfig) : Msg
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

    private inner class ExecutorImpl : CoroutineExecutor<ListIntent, Action, ListState, Msg, ListLabel>() {
        override fun executeAction(action: Action, getState: () -> ListState) {
            when (action) {
                LoadLastConfig -> {
                    scope.launch(deps.dispatchers.io) {
                        val type = getLastListId()
                        setList(type, onComplete = ::showTutorialIfNeeded)
                    }
                }
            }
        }

        override fun executeIntent(intent: ListIntent, getState: () -> ListState) {
            when (intent) {
                Add -> dispatch(Msg.AddShloka)
                Save -> saveList(getState().config)
                CheckLocale -> checkLocale(getState().config)
                CheckPreRating -> InappReviewInteractor.check { showPreRating() }
                PreRatingAccepted -> preRatingAccepted()
                PreRatingClosed -> preRatingClosed()
                TutorialCompleted -> tutorialCompleted()
                TutorialSkipped -> tutorialSkipped()
                is SetList -> scope.launch { setList(intent.type) }
                is Title -> rename(getState().config.title, intent.title)
                is Remove -> dispatch(Msg.RemoveShloka(intent.id))
                is MoveUp -> dispatch(Msg.MoveUp(intent.id))
                is MoveDown -> dispatch(Msg.MoveDown(intent.id))
                is Select -> select(intent.id, intent.isSelected)
                is SaveShloka -> saveShloka(getState().config, intent.config)
            }
        }

        private suspend fun setList(type: ListId, onComplete: suspend () -> Unit = {}) {
            val config = readConfig(type, deps.configReader)
            config?.let {
                deps.config = config.updateList()
                withContext(deps.dispatchers.main) {
                    dispatch(Msg.SetList(deps.config))
                    onComplete.invoke()
                }
            }
        }

        private fun select(id: String, isSelected: Boolean) {
            selectShloka(id, isSelected)
            dispatch(Msg.Select(id, isSelected))
        }

        private suspend fun showTutorialIfNeeded() {
            if (shouldShowTutorial() && !tutorialWasShownInThisSession) {
                delay(TUTORIAL_DELAY_MS)
                deps.analytics.tutorialOpen()
                tutorialWasShownInThisSession = true
                dispatch(Msg.ShowTutorial)
            }
        }

        private fun checkLocale(config: ListConfig) {
            if (locale != getLocale()) {
                locale = getLocale()
                deps.config = config.updateList()
                dispatch(Msg.Update(deps.config))
            }
        }

        private fun showPreRating() {
            onPreRatingShown()
            deps.analytics.preratingShown()
            dispatch(Msg.ShowPreRating)
        }

        private fun preRatingAccepted() {
            deps.analytics.preratingAccepted()
            dispatch(Msg.ClosePreRating)
            publish(ListLabel.ShowInappReview)
        }

        private fun preRatingClosed() {
            onPreRatingClosed()
            deps.analytics.preratingClosed()
            dispatch(Msg.ClosePreRating)
        }

        private fun tutorialCompleted() {
            setTutorialCompleted()
            deps.analytics.tutorialComplete(AnalyticsScreen.LIST)
            dispatch(Msg.TutorialCompleted)
        }

        private fun tutorialSkipped() {
            onTutorialSkipped()
            deps.analytics.tutorialSkip()
            dispatch(Msg.TutorialSkipped)
        }

        private fun ListConfig.updateList() =
            copy(
                title = resolveListTitle(id),
                list = list.map {
                    it.copy(
                        shloka = it.shloka.copy(title = resolveTitle(it.shloka.id)),
                        isSelected = isSelected(it.shloka.id)
                    )
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
                Msg.ShowTutorial -> copy(shouldShowTutorial = true)
                Msg.TutorialCompleted -> copy(shouldShowTutorial = false)
                Msg.TutorialSkipped -> copy(shouldShowTutorial = false)
                Msg.ShowPreRating -> copy(shouldShowPreRating = true)
                Msg.ClosePreRating -> copy(shouldShowPreRating = false)
                is Msg.SetList -> setList(newConfig = msg.config)
                is Msg.Update -> update(newConfig = msg.config)
                is Msg.Title -> update(newConfig = config.copy(title = msg.title))
                is Msg.RemoveShloka -> update(newConfig = config.remove(msg.id))
                is Msg.MoveUp -> update(newConfig = config.moveUp(msg.id))
                is Msg.MoveDown -> update(newConfig = config.moveDown(msg.id))
                is Msg.Select -> update(newConfig = config.select(msg.id, msg.isSelected))
            }

        private fun ListState.setList(newConfig: ListConfig): ListState {
            saveLastListId(newConfig.id.id)
            return copy(
                config = newConfig,
                availableLists = availableLists(),
                hasChanges = newConfig != deps.config,
            )
        }

        private fun ListState.update(newConfig: ListConfig): ListState {
            saveLastListId(newConfig.id.id)
            return copy(
                config = newConfig,
                hasChanges = newConfig != deps.config,
            )
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

    private fun shouldShowTutorial() =
        getAppLaunchCount().let {
           (it == 3 || it == 7) && !isTutorialCompleted()
        }

    private fun availableLists() =
        ListId.values().map {
            ListPresentation(
                type = it,
                title = resolveListShortTitle(it),
                isSelected = deps.config.id == it
            )
        }
}
