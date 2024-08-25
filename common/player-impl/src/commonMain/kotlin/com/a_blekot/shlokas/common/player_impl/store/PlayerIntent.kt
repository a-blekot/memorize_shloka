package com.a_blekot.shlokas.common.player_impl.store

import com.a_blekot.shlokas.common.data.RepeatMode

sealed interface PlayerIntent {
    data object ForcePlay : PlayerIntent
    data object ForcePause : PlayerIntent
    data object Stop : PlayerIntent
    data object Prev : PlayerIntent
    data object Next : PlayerIntent
    data class RepeatModeChanged(val newMode: RepeatMode) : PlayerIntent
}
