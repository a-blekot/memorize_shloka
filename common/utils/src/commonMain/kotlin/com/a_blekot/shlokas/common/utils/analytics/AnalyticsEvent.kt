package com.a_blekot.shlokas.common.utils.analytics

enum class AnalyticsEvent(val title: String) {
    PRERATING_SHOWN("prerating_shown"),
    PRERATING_ACCEPTED("prerating_accepted"),
    PRERATING_CLOSED("prerating_closed"),
    TUTORIAL_OPEN("tutorial_open"),
    TUTORIAL_SETTINGS("tutorial_settings"),
    TUTORIAL_SKIP("tutorial_skip"),
    TUTORIAL_COMPLETE("tutorial_complete"),
    PLAY_LIST("play_list"),
    PLAY_SHLOKA("play_shloka"),
    PLAY_COMPLETED("play_completed")
}
