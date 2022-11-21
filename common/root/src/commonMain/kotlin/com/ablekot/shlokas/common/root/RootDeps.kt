package com.ablekot.shlokas.common.root

import com.a_blekot.shlokas.common.data.PlatformApi
import com.a_blekot.shlokas.common.player_api.PlayerBus
import com.a_blekot.shlokas.common.utils.analytics.Analytics
import com.a_blekot.shlokas.common.utils.resources.ConfigReader
import com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider
import com.a_blekot.shlokas.common.utils.Filer
import com.a_blekot.shlokas.common.utils.billing.BillingHelper
import com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserver
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler

data class RootDeps(
    val filer: Filer,
    val analytics: Analytics,
    val playerBus: PlayerBus,
    val platformApi: PlatformApi,
    val dispatchers: DispatcherProvider,
    val configReader: ConfigReader,
    val billingHelper: BillingHelper?,
    val connectivityObserver: ConnectivityObserver,
    val stringResourceHandler: StringResourceHandler,
)