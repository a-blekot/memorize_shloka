package com.a_blekot.shlokas.common.player_api

data class PlayerState(
    val title: String = "",
    val filePath: String = "",
    val description: String = "",
    val timeMs: Long = 0L,
    val durationMs: Long = 0L,
    val isPlaying: Boolean = false,
)