package com.a_blekot.shlokas.common.player_impl

import com.a_blekot.shlokas.common.data.PlayConfig
import com.a_blekot.shlokas.common.player_api.PlayerBus
import com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler

data class PlayerDeps(
    val config: PlayConfig,
    val playerBus: PlayerBus,
    val stringResourceHandler: StringResourceHandler,
    val dispatchers: DispatcherProvider,
)
