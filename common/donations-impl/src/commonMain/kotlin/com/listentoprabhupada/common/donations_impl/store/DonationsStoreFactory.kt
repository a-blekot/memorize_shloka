package com.listentoprabhupada.common.donations_impl.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.listentoprabhupada.common.donations_impl.DonationsDeps
import com.listentoprabhupada.common.donations_api.DonationsState
import io.github.aakira.napier.Napier

internal class DonationsStoreFactory(
    private val storeFactory: StoreFactory,
    private val deps: DonationsDeps
) {

    fun create(): DonationsStore =
        object : DonationsStore, Store<DonationsIntent, DonationsState, DonationsLabel> by storeFactory.create(
            name = "DonationsStore",
            initialState = DonationsState(),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        object StartLoading : Msg
        data class LoadingComplete(val state: DonationsState = DonationsState()) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<DonationsIntent, Unit, DonationsState, Msg, DonationsLabel>() {
        override fun executeAction(action: Unit, getState: () -> DonationsState) {}

        override fun executeIntent(intent: DonationsIntent, getState: () -> DonationsState) {
            if (getState().isLoading) {
                Napier.d("executeIntent canceled, isLoading = true!", tag = "DonationsStoreExecutor")
                return
            }

            when (intent) {
                DonationsIntent.Next -> {}
                DonationsIntent.Prev -> {}
                else -> {
                    /** do nothing **/
                }
            }
        }
    }

    private object ReducerImpl : Reducer<DonationsState, Msg> {
        override fun DonationsState.reduce(msg: Msg): DonationsState =
            when (msg) {
                Msg.StartLoading -> copy(isLoading = true)
                is Msg.LoadingComplete -> msg.state
            }
    }
}
