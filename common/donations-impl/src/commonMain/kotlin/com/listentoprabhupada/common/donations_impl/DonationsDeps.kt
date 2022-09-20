package com.listentoprabhupada.common.donations_impl

import com.a_blekot.shlokas.common.utils.analytics.Analytics
import com.a_blekot.shlokas.common.utils.billing.BillingHelper
import com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler

data class DonationsDeps(
    val analytics: Analytics,
    val billingHelper: BillingHelper?,
    val dispatchers: DispatcherProvider,
    val stringResourceHandler: StringResourceHandler
)
