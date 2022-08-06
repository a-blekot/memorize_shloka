package com.a_blekot.shlokas.common.settings_api

import com.arkivanov.decompose.value.Value

interface SettingsComponent {
    val flow: Value<SettingsState>

    fun setRepeats(value: Int) {}
    fun setWeek(value: Int) {}
}