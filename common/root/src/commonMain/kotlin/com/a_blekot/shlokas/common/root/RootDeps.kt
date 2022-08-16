package com.a_blekot.shlokas.common.root

import com.a_blekot.shlokas.common.player_api.PlayerBus
import com.a_blekot.shlokas.common.utils.analytics.Analytics
import com.a_blekot.shlokas.common.utils.resources.ConfigReader
import com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider
import com.a_blekot.shlokas.common.utils.Filer
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler

data class RootDeps(
    val filer: Filer,
    val configReader: ConfigReader,
    val stringResourceHandler: StringResourceHandler,
    val playerBus: PlayerBus,
    val analytics: Analytics,
    val dispatchers: DispatcherProvider,
    val onEmail: () -> Unit
)