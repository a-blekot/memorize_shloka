package com.listentoprabhupada.common.donations_impl.store

import com.a_blekot.shlokas.common.data.Donation
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.listentoprabhupada.common.donations_impl.DonationsDeps
import com.listentoprabhupada.common.donations_api.DonationsState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class DonationsStoreFactory(
    private val storeFactory: StoreFactory,
    private val deps: DonationsDeps
) : StringResourceHandler by deps.stringResourceHandler{

    fun create(): DonationsStore =
        object : DonationsStore, Store<DonationsIntent, DonationsState, DonationsLabel> by storeFactory.create(
            name = "DonationsStore",
            autoInit = false,
            initialState = DonationsState(deps.billingHelper?.availableDonations?.loadTitles() ?: emptyList()),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        object ShowNamaste : Msg
        data class LoadingComplete(val donations: List<Donation>) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<DonationsIntent, Unit, DonationsState, Msg, DonationsLabel>() {
        override fun executeIntent(intent: DonationsIntent, getState: () -> DonationsState) {

            when (intent) {
                is DonationsIntent.Purchase -> deps.billingHelper?.purchase(intent.donation)
                is DonationsIntent.LoadingComplete -> dispatch(Msg.LoadingComplete(intent.donations.loadTitles()))
                DonationsIntent.SuccessPurchase -> successPurchase()
            }
        }

        private fun successPurchase() {
            scope.launch {
                dispatch(Msg.ShowNamaste)
                delay(2600L)
                publish(DonationsLabel.SuccessPurchase)
            }
        }
    }

    private object ReducerImpl : Reducer<DonationsState, Msg> {
        override fun DonationsState.reduce(msg: Msg): DonationsState =
            when (msg) {
                is Msg.LoadingComplete -> copy(donations = msg.donations)
                Msg.ShowNamaste -> copy(showNamaste = true)
            }
    }

    private fun List<Donation>.loadTitles() =
        map { it.copy(title = resolveDonationTitle(it.donationLevel)) }
}
