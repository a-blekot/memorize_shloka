package com.a_blekot.shlokas.common.player_api

import com.arkivanov.decompose.value.Value

interface PlayerComponent {
    val flow: Value<PlayerState>

    fun forcePlay() {}
    fun forcePause() {}
    fun restart() {}
    fun stop() {}

    fun prev() {}
    fun next() {}
}