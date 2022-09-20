package com.listentoprabhupada.common.donations_impl.store

import com.a_blekot.shlokas.common.data.Donation

sealed interface DonationsIntent {
    data class LoadingComplete(val donations: List<Donation>) : DonationsIntent
    data class Purchase(val donation: Donation) : DonationsIntent
    object SuccessPurchase : DonationsIntent
}
