package com.a_blekot.shlokas.common.settings_impl.store

import com.a_blekot.shlokas.common.data.Week

sealed interface SettingsIntent {
    data class Repeats(val value: Int) : SettingsIntent
    data class Weeks(val value: Int) : SettingsIntent
}
