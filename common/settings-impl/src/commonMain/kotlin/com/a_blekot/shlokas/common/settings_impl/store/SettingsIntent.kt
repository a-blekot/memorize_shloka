package com.a_blekot.shlokas.common.settings_impl.store

sealed interface SettingsIntent {
    data class Repeats(val value: Int) : SettingsIntent
    data class Pause(val value: Long) : SettingsIntent
    data class Weeks(val value: Int) : SettingsIntent
    data class Locale(val value: String) : SettingsIntent
    data class Autoplay(val value: Boolean) : SettingsIntent
    data class WithTranslation(val value: Boolean) : SettingsIntent
    data class ShowClosePlayerDialog(val value: Boolean) : SettingsIntent
}
