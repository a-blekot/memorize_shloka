package com.a_blekot.shlokas.common.settings_api

import com.a_blekot.shlokas.common.data.RepeatMode
import kotlinx.serialization.Serializable

@Serializable
data class SettingsState(
    val repeats: Int,
    val pause: Long,
    val repeatMode: RepeatMode,
    val locale: String,
    val isAutoplay: Boolean,
    val withSanskrit: Boolean,
    val withTranslation: Boolean,
    val showClosePlayerDialog: Boolean,
)
