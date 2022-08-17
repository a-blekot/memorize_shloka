package com.a_blekot.shlokas.common.player_impl.store

sealed interface PlayerIntent {
    object ForcePlay : PlayerIntent
    object ForcePause : PlayerIntent
    object Restart : PlayerIntent
    object Stop : PlayerIntent
}
