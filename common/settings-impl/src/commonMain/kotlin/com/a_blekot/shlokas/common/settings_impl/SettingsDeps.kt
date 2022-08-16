package com.a_blekot.shlokas.common.settings_impl

import com.a_blekot.shlokas.common.utils.analytics.Analytics
import com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider

data class SettingsDeps(
    val analytics: Analytics,
    val dispatchers: DispatcherProvider,
)
