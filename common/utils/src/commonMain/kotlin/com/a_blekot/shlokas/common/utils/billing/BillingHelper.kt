package com.a_blekot.shlokas.common.utils.billing

import com.a_blekot.shlokas.common.data.Donation
import kotlinx.coroutines.flow.SharedFlow

interface BillingHelper {
    val availableDonations: List<Donation>
    val events: SharedFlow<BillingEvent>

    fun purchase(donation: Donation) {}
    fun checkUnconsumedPurchases() {}
}