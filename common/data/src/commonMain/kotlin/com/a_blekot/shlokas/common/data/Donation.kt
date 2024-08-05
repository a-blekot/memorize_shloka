package com.a_blekot.shlokas.common.data

import kotlinx.serialization.Serializable

@Serializable
data class Donation(
    val donationLevel: DonationLevel,
    val formattedPrice: String,
    val priceAmountMicros: Long,
    val title: String = "",
)
