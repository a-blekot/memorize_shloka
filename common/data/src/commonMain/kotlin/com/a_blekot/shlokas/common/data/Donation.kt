package com.a_blekot.shlokas.common.data

data class Donation(
    val donationLevel: DonationLevel,
    val formattedPrice: String,
    val priceAmountMicros: Long,
    val title: String = "",
)
