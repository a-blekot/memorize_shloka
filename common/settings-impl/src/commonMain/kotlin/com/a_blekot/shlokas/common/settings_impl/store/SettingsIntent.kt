package com.a_blekot.shlokas.common.settings_impl.store

sealed interface SettingsIntent {
    data class Repeats(val value: Int) : SettingsIntent
    data class Weeks(val value: Int) : SettingsIntent
    data class Autoplay(val value: Boolean) : SettingsIntent
}