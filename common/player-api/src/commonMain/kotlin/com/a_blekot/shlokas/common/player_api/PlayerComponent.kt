package com.a_blekot.shlokas.common.player_api

import com.arkivanov.decompose.value.Value

interface PlayerComponent {
    val flow: Value<PlayerState>

    fun play() {}
    fun pause() {}
    fun restart() {}
    fun stop() {}
}