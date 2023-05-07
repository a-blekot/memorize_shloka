package com.a_blekot.shlokas.common.settings_impl.store

import com.a_blekot.shlokas.common.data.Week
import com.a_blekot.shlokas.common.settings_api.SettingsState
import com.a_blekot.shlokas.common.settings_impl.store.SettingsIntent.*
import com.a_blekot.shlokas.common.settings_impl.store.SettingsStoreFactory.Action.CheckFtue
import com.a_blekot.shlokas.common.utils.*
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch

internal class SettingsStoreFactory(
    private val storeFactory: StoreFactory,
    private val initialState: SettingsState,
) {
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
        object CheckFtue : Action
    }

    sealed interface Msg {
        data class Repeats(val value: Int) : Msg
        data class Pause(val value: Long) : Msg
        data class Weeks(val value: Week) : Msg
        data class Locale(val value: String) : Msg
        data class Autoplay(val value: Boolean) : Msg
        data class WithSanskrit(val value: Boolean) : Msg
        data class WithTranslation(val value: Boolean) : Msg
        data class ShowClosePlayerDialog(val value: Boolean) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(CheckFtue)
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<SettingsIntent, Action, SettingsState, Msg, SettingsLabel>() {
        override fun executeAction(action: Action, getState: () -> SettingsState) {
            when (action) {
                CheckFtue -> checkFtue()
            }
        }

        override fun executeIntent(intent: SettingsIntent, getState: () -> SettingsState) {
            when (intent) {
                is Repeats -> setRepeats(intent.value)
                is Pause -> setPause(intent.value)
                is Weeks -> setWeek(intent.value)
                is Locale -> setLocale(intent.value)
                is Autoplay -> setAutoplay(intent.value)
                is WithSanskrit -> setWithSanskrit(intent.value)
                is WithTranslation -> setWithTranslation(intent.value)
                is ShowClosePlayerDialog -> updateShowClosePlayerDialog(intent.value)
            }
        }

        private fun setRepeats(value: Int) {
            val savedValue = saveRepeats(value) // coerce in range
            dispatch(Msg.Repeats(savedValue))
        }

        private fun setPause(value: Long) {
            val savedValue = savePause(value) // coerce in range
            dispatch(Msg.Pause(savedValue))
        }

        private fun setWeek(value: Int) {
            val week = weekFromOrdinal(value)
            saveCurrentWeek(week)
            dispatch(Msg.Weeks(week))
        }

        private fun setLocale(value: String) {
            setAppLocale(value)
            dispatch(Msg.Locale(value))
        }

        private fun setAutoplay(value: Boolean) {
            saveAutoPlay(value)
            dispatch(Msg.Autoplay(value))
        }

        private fun setWithSanskrit(value: Boolean) {
            saveWithSanskrit(value)
            dispatch(Msg.WithSanskrit(value))
        }

        private fun setWithTranslation(value: Boolean) {
            saveWithTranslation(value)
            dispatch(Msg.WithTranslation(value))
        }

        private fun updateShowClosePlayerDialog(value: Boolean) {
            setShowClosePlayerDialog(value)
            dispatch(Msg.ShowClosePlayerDialog(value))
        }

        private fun checkFtue() {
        }
    }

    private inner class ReducerImpl : Reducer<SettingsState, Msg> {
        override fun SettingsState.reduce(msg: Msg): SettingsState =
            when (msg) {
                is Msg.Repeats -> copy(repeats = msg.value)
                is Msg.Pause -> copy(pause = msg.value)
                is Msg.Weeks -> copy(week = msg.value)
                is Msg.Locale -> copy(locale = msg.value)
                is Msg.Autoplay -> copy(isAutoplay = msg.value)
                is Msg.WithSanskrit -> copy(withSanskrit = msg.value)
                is Msg.WithTranslation -> copy(withTranslation = msg.value)
                is Msg.ShowClosePlayerDialog -> copy(showClosePlayerDialog = msg.value)
            }
    }
}
