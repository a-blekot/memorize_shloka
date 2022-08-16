package com.a_blekot.shlokas.common.settings_api

import com.arkivanov.decompose.value.Value

interface SettingsComponent {
    val flow: Value<SettingsState>

    fun setRepeats(value: Int) {}
    fun setPause(value: Long) {}
    fun setWeek(value: Int) {}
    fun setLocale(value: String) {}
    fun setAutoplay(value: Boolean) {}
    fun onShowTutorial() {}
    fun onTutorialCompleted() {}
    fun sendEmail() {}
}