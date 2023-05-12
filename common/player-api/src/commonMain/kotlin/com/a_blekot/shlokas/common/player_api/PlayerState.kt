package com.a_blekot.shlokas.common.player_api

import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.player_api.PlaybackState.IDLE
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class PlayerState(
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
) : Parcelable

@Parcelize
enum class PlaybackState : Parcelable {
    IDLE,
    PLAYING,
    PAUSED,
    FORCE_PAUSED,
    NO_AUDIO
}