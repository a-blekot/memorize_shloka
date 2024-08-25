package com.a_blekot.shlokas.common.player_api

import com.a_blekot.shlokas.common.data.RepeatMode
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.player_api.PlaybackState.IDLE
import kotlinx.serialization.Serializable

@Serializable
data class PlayerState(
    val repeatMode: RepeatMode = RepeatMode.ONE_LINE,
    val hasAudio: Boolean = true,
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
    val startShloka: ShlokaConfig? = null,
)

enum class PlaybackState  {
    IDLE,
    PLAYING,
    PAUSED,
    FORCE_PAUSED,
    NO_AUDIO
}