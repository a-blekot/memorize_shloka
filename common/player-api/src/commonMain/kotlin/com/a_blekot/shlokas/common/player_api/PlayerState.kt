package com.a_blekot.shlokas.common.player_api

data class PlayerState(
    val title: String = "",
    val filePath: String = "",
    val sanskrit: String = "",
    val wordsTranslation: String = "",
    val translation: String = "",
    val timeMs: Long = 0L,
    val durationMs: Long = 0L,
    val isPlaying: Boolean = false,
    val currentRepeat: Int = 1,
    val totalRepeats: Int = 1,
)