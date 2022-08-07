package com.a_blekot.shlokas.common.settings_impl.store

import com.a_blekot.shlokas.common.data.Week
import com.a_blekot.shlokas.common.settings_api.SettingsState
import com.a_blekot.shlokas.common.settings_impl.SettingsDeps
import com.a_blekot.shlokas.common.settings_impl.store.SettingsIntent.Repeats
import com.a_blekot.shlokas.common.settings_impl.store.SettingsIntent.Weeks
import com.a_blekot.shlokas.common.settings_impl.store.SettingsStoreFactory.Action.CeckFtue
import com.a_blekot.shlokas.common.utils.*
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch

internal class SettingsStoreFactory(
    private val storeFactory: StoreFactory,
    private val deps: SettingsDeps
) {
    private val initialState
        get() = SettingsState(
            getRepeats(),
            getCurrentWeek(),
            getAutoPlay()
        )

    fun create(): SettingsStore =
        object : SettingsStore, Store<SettingsIntent, SettingsState, SettingsLabel> by storeFactory.create(
            name = "SettingsStore",
            autoInit = false,
            initialState = initialState,
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl()
        ) {}

    sealed interface Action {
        object CeckFtue : Action
    }

    sealed interface Msg {
        data class Repeats(val value: Int) : Msg
        data class Weeks(val value: Week) : Msg
        data class Autoplay(val value: Boolean) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(CeckFtue)
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<SettingsIntent, Action, SettingsState, Msg, SettingsLabel>() {
        override fun executeAction(action: Action, getState: () -> SettingsState) {
            when (action) {
                CeckFtue -> checkFtue()
            }
        }

        override fun executeIntent(intent: SettingsIntent, getState: () -> SettingsState) {
            when (intent) {
                is Repeats -> setRepeats(intent.value)
                is Weeks -> setWeek(intent.value)
                is SettingsIntent.Autoplay -> setAutoplay(intent.value)
            }
        }

        private fun setRepeats(value: Int) {
            saveRepeats(value.coerceIn(1, 100))
            dispatch(Msg.Repeats(value))
        }

        private fun setWeek(value: Int) {
            val week = weekFromOrdinal(value)
            saveCurrentWeek(week)
            dispatch(Msg.Weeks(week))
        }

        private fun setAutoplay(value: Boolean) {
            saveAutoPlay(value)
            dispatch(Msg.Autoplay(value))
        }

        private fun checkFtue() {
        }

//        private fun stop() {
//            dispatch(Msg.Pause)
//            publish(SettingsLabel.Stop)
//        }
    }

    private inner class ReducerImpl : Reducer<SettingsState, Msg> {
        override fun SettingsState.reduce(msg: Msg): SettingsState =
            when (msg) {
                is Msg.Repeats -> copy(repeats = msg.value)
                is Msg.Weeks -> copy(week = msg.value)
                is Msg.Autoplay -> copy(isAutoplay = msg.value)
            }
    }
}
