package com.a_blekot.shlokas.common.player_impl.store

sealed interface PlayerIntent {
    object Play : PlayerIntent
    object Pause : PlayerIntent
    object Restart : PlayerIntent
    object Stop : PlayerIntent
}
