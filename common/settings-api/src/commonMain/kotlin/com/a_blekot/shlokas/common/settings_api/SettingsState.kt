package com.a_blekot.shlokas.common.settings_api

import com.a_blekot.shlokas.common.data.Week
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class SettingsState(
    val repeats: Int,
    val pause: Long,
    val week: Week,
    val locale: String,
    val isAutoplay: Boolean,
    val showClosePlayerDialog: Boolean,
): Parcelable
