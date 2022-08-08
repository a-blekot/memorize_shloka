package com.a_blekot.shlokas.common.settings_api

import com.a_blekot.shlokas.common.data.Week

data class SettingsState(
    val repeats: Int,
    val pause: Long,
    val week: Week,
    val locale: String,
    val isAutoplay: Boolean,
)
