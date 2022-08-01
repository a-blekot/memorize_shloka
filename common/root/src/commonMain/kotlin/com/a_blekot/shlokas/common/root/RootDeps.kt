package com.a_blekot.shlokas.common.root

import com.a_blekot.shlokas.common.player_api.PlayerBus
import com.a_blekot.shlokas.common.utils.DispatcherProvider
import com.a_blekot.shlokas.common.utils.Filer

data class RootDeps(
    val filer: Filer,
    val playerBus: PlayerBus,
    val dispatchers: DispatcherProvider,
)