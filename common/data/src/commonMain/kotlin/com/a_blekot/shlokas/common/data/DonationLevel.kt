package com.a_blekot.shlokas.common.data

import kotlinx.serialization.Serializable

@Serializable
enum class DonationLevel(val productId: String) {
    USD_1("donate_1_usd"),
    USD_2("donate_2_usd"),
    USD_3("donate_3_usd"),
    USD_5("donate_5_usd"),
    USD_10("donate_10_usd"),
    USD_25("donate_25_usd"),
}

fun getDonationLevel(productId: String) =
    DonationLevel.entries.firstOrNull { it.productId == productId }
