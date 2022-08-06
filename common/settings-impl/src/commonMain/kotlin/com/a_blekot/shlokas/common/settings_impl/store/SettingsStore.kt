package com.a_blekot.shlokas.common.settings_impl.store

import com.a_blekot.shlokas.common.settings_api.SettingsState
import com.arkivanov.mvikotlin.core.store.Store

internal interface SettingsStore : Store<SettingsIntent, SettingsState, SettingsLabel>
