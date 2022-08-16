package com.a_blekot.shlokas.common.utils.analytics

enum class AnalyticsEvent(val title: String) {
    TUTORIAL_OPEN("tutorial_open"),
    TUTORIAL_SETTINGS("tutorial_settings"),
    TUTORIAL_SKIP("tutorial_skip"),
    TUTORIAL_COMPLETE("tutorial_complete"),
    PLAY_LIST("play_list"),
    PLAY_COMPLETED("play_completed")
}
