package com.a_blekot.shlokas.common.player_api

import com.a_blekot.shlokas.common.player_api.PlaybackState.IDLE

data class PlayerState(
    val title: String = "",
    val sanskrit: String = "",
    val words: String = "",
    val translation: String = "",
    val durationMs: Long = 0L,
    val playbackState: PlaybackState = IDLE,
    val currentRepeat: Int = 0,
    val totalRepeats: Int = 1,
    val currentShlokaIndex: Int = 0,
    val totalShlokasCount: Int = 1,
    val totalDurationMs: Long = 0L,
    val isAutoplay: Boolean = true,
)

enum class PlaybackState {
    IDLE,
    PLAYING,
    PAUSED,
    STOPPED
}