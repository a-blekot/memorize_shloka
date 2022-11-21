package com.a_blekot.shlokas.common.settings_api

sealed interface SettingsOutput {
    object Donations: SettingsOutput
    object Back: SettingsOutput
}