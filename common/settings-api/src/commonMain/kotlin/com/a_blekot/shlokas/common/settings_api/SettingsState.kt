package com.a_blekot.shlokas.common.settings_api

import com.a_blekot.shlokas.common.data.RepeatMode
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class SettingsState(
    val repeats: Int,
    val pause: Long,
    val repeatMode: RepeatMode,
    val locale: String,
    val isAutoplay: Boolean,
    val withSanskrit: Boolean,
    val withTranslation: Boolean,
    val showClosePlayerDialog: Boolean,
): Parcelable
