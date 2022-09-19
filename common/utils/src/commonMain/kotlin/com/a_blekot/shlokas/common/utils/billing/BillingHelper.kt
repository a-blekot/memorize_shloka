package com.a_blekot.shlokas.common.utils.billing

interface BillingHelper {
    val availableDonations: List<DonationProduct>

    fun purchase(donation: DonationProduct) {}

    fun checkUnconsumedPurchases() {}

    fun onPurchaseSuccess(donation: DonationProduct?) {}
}