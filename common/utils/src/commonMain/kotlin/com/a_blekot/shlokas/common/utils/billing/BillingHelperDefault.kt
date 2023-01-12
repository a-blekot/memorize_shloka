package com.a_blekot.shlokas.common.utils.billing

import com.a_blekot.shlokas.common.data.Donation
import com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BillingHelperDefault(
    dispatchers: DispatcherProvider
): BillingHelper {

    private val scope = CoroutineScope(dispatchers.main) + SupervisorJob()
    private val _events = MutableSharedFlow<BillingEvent>()

    override val availableDonations = mutableListOf<Donation>()
    override val events = _events.asSharedFlow()

    fun onSuccessPurchase(productId: String) =
        availableDonations
            .firstOrNull { it.donationLevel.productId == productId }
            ?.let { emitEvent(BillingEvent.PurchaseSuccess(it)) }

    fun emitEvent(event: BillingEvent) =
        scope.launch {
            _events.emit(event)
        }

    fun updateDonations(list: List<Donation>) {
        availableDonations.apply {
            clear()
            addAll(list)
        }
        emitEvent(BillingEvent.DonationsLoaded(list))
    }
}