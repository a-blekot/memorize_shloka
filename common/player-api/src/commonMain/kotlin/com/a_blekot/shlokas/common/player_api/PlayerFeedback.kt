package com.a_blekot.shlokas.common.player_api

sealed interface PlayerFeedback {
    object Ready: PlayerFeedback
    object Started: PlayerFeedback
    object Completed: PlayerFeedback
}