package com.a_blekot.shlokas.common.player_impl

import com.a_blekot.shlokas.common.data.PlayConfig
import com.a_blekot.shlokas.common.player_api.PlayerBus
import com.a_blekot.shlokas.common.utils.DispatcherProvider

data class PlayerDeps(
    val config: PlayConfig,
    val playerBus: PlayerBus,
    val dispatchers: DispatcherProvider,
)
