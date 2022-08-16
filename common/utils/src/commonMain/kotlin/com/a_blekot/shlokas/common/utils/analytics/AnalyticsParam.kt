package com.a_blekot.shlokas.common.utils.analytics

enum class AnalyticsParam {
    COUNT,
    SCREEN,
    LAUNCH_COUNT,
    REPEATS,
    DURATION,
    LIST_ID,
    SHLOKA_ID;

    val low
        get() = name.lowercase()
}