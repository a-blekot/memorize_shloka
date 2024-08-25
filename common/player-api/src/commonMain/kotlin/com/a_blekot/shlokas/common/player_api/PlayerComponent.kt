package com.a_blekot.shlokas.common.player_api

import com.a_blekot.shlokas.common.data.RepeatMode
import com.arkivanov.decompose.value.Value

interface PlayerComponent {
    val flow: Value<PlayerState>

    fun forcePlay() {}
    fun forcePause() {}
    fun stop() {}

    fun prev() {}
    fun next() {}

    fun repeatModeChanged(newMode: RepeatMode) {}
}