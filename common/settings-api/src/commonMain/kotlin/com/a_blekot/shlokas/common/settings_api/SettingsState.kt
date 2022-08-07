package com.a_blekot.shlokas.common.settings_api

import com.a_blekot.shlokas.common.data.Week

data class SettingsState(
    val repeats: Int,
    val week: Week,
    val isAutoplay: Boolean,
)
