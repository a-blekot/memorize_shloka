package com.a_blekot.shlokas.common.settings_impl.store

import com.a_blekot.shlokas.common.data.RepeatMode
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
        data object CheckFtue : Action
    }

    sealed interface Msg {
        data class Repeats(val value: Int) : Msg
        data class Pause(val value: Long) : Msg
        data class RepeatMode(val value: com.a_blekot.shlokas.common.data.RepeatMode) : Msg
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
        override fun executeAction(action: Action) {
            when (action) {
                CheckFtue -> checkFtue()
            }
        }

        override fun executeIntent(intent: SettingsIntent) {
            when (intent) {
                is Repeats -> setRepeats(intent.value)
                is Pause -> setPause(intent.value)
                is SettingsIntent.RepeatMode -> setRepeatMode(intent.value)
                is Locale -> setLocale(intent.value)
                is Autoplay -> setAutoplay(intent.value)
                is WithSanskrit -> setWithSanskrit(intent.value)
                is WithTranslation -> setWithTranslation(intent.value)
                is ShowClosePlayerDialog -> updateShowClosePlayerDialog(intent.value)
            }
        }

        private fun setRepeats(value: Int) {
            repeats = value // coerce in range
            dispatch(Msg.Repeats(repeats))
        }

        private fun setPause(value: Long) {
            pause = value // coerce in range
            dispatch(Msg.Pause(pause))
        }

        private fun setRepeatMode(value: Int) {
            val result = RepeatMode.fromOrdinal(value)
            repeatMode = result
            dispatch(Msg.RepeatMode(result))
        }

        private fun setLocale(value: String) {
            setAppLocale(value)
            dispatch(Msg.Locale(value))
        }

        private fun setAutoplay(value: Boolean) {
            autoPlay = value
            dispatch(Msg.Autoplay(value))
        }

        private fun setWithSanskrit(value: Boolean) {
            withSanskrit = value
            dispatch(Msg.WithSanskrit(value))
        }

        private fun setWithTranslation(value: Boolean) {
            withTranslation = value
            dispatch(Msg.WithTranslation(value))
        }

        private fun updateShowClosePlayerDialog(value: Boolean) {
            showClosePlayerDialog = value
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
                is Msg.RepeatMode -> copy(repeatMode = msg.value)
                is Msg.Locale -> copy(locale = msg.value)
                is Msg.Autoplay -> copy(isAutoplay = msg.value)
                is Msg.WithSanskrit -> copy(withSanskrit = msg.value)
                is Msg.WithTranslation -> copy(withTranslation = msg.value)
                is Msg.ShowClosePlayerDialog -> copy(showClosePlayerDialog = msg.value)
            }
    }
}
