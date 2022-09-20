package com.listentoprabhupada.common.donations_api

import com.a_blekot.shlokas.common.data.Donation

data class DonationsState(
    val donations: List<Donation> = emptyList(),
    val showNamaste: Boolean = false
)
