package com.listentoprabhupada.common.donations_api

import com.a_blekot.shlokas.common.data.Donation
import kotlinx.serialization.Serializable

@Serializable
data class DonationsState(
    val donations: List<Donation> = emptyList(),
    val connectionStatus: Boolean = true,
    val showNamaste: Boolean = false
)
