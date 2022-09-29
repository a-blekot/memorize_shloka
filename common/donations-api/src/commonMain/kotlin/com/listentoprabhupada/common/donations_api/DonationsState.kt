package com.listentoprabhupada.common.donations_api

import com.a_blekot.shlokas.common.data.Donation
import com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserver
import com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserver.Status.Available

data class DonationsState(
    val donations: List<Donation> = emptyList(),
    val connectionStatus: ConnectivityObserver.Status = Available,
    val showNamaste: Boolean = false
)
