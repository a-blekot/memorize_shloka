package com.a_blekot.shlokas.common.player_api

data class PlayerState(
    val title: String = "",
    val sanskrit: String = "",
    val words: String = "",
    val translation: String = "",
    val timeMs: Long = 0L,
    val durationMs: Long = 0L,
    val isPlaying: Boolean = false,
    val currentRepeat: Int = 0,
    val totalRepeats: Int = 1,
    val currentShlokaIndex: Int = 1,
    val totalShlokasCount: Int = 1,
    val totalDurationMs: Long = 0L
)