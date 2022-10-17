package com.listentoprabhupada.common.donations_api

import com.a_blekot.shlokas.common.data.Donation
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class DonationsState(
    val donations: List<Donation> = emptyList(),
    val connectionStatus: Boolean = true,
    val showNamaste: Boolean = false
): Parcelable
