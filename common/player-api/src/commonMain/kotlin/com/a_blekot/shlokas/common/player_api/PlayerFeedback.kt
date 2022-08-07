package com.a_blekot.shlokas.common.player_api

sealed interface PlayerFeedback {
    object Ready: PlayerFeedback
    data class Started(val durationMs: Long): PlayerFeedback
//    object Completed: PlayerFeedback
}