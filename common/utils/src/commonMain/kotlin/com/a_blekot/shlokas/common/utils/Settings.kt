package com.a_blekot.shlokas.common.utils

import com.a_blekot.shlokas.common.data.Week
import com.russhwolf.settings.Settings
import io.github.aakira.napier.Napier

private const val APP_LAUNCH_COUNT = "APP_LAUNCH_COUNT"
private const val CURRENT_REPEATS = "CURRENT_REPEATS"
private const val CURRENT_WEEK = "CURRENT_WEEK"
private const val LOCALE_KEY = "LOCALE_KEY"
private const val LAST_CONFIG_ID_KEY = "LAST_CONFIG_NAME_KEY"
private const val AUTOPLAY_KEY = "AUTOPLAY_KEY"
private const val PAUSE_AFTER_EACH = "PAUSE_AFTER_EACH"
private const val TUTORIAL_COMPLETED_KEY = "TUTORIAL_COMPLETED_KEY"

private const val DEFAULT_REPEATS = 10
private const val MAX_REPEATS = 16_108

private const val MIN_PAUSE = 100L
private const val DEFAULT_PAUSE = 500L
private const val MAX_PAUSE = 10_000L

private val settings = Settings()

fun onAppLaunch() =
    getAppLaunchCount().let {
        settings.putInt(APP_LAUNCH_COUNT, it + 1)
    }

fun getAppLaunchCount() =
    settings.getInt(APP_LAUNCH_COUNT)

fun saveLastConfigId(id: String) =
    settings.getString(LAST_CONFIG_ID_KEY, id)

fun getLastConfigId() =
    settings.getString(LAST_CONFIG_ID_KEY)

fun saveAutoPlay(value: Boolean) =
    settings.putBoolean(AUTOPLAY_KEY, value)

fun getAutoPlay() =
    settings.getBoolean(AUTOPLAY_KEY, true)

fun saveCurrentWeek(week: Week) =
    settings.putInt(CURRENT_WEEK, week.ordinal)

fun getCurrentWeek() =
    settings.getInt(CURRENT_WEEK).let {
        weekFromOrdinal(it)
    }

fun saveLocale(locale: String) =
    settings.putString(LOCALE_KEY, locale)

fun getLocale() =
    settings.getString(LOCALE_KEY)

fun saveRepeats(repeats: Int): Int {
    val savedValue = repeats.coerceIn(1, MAX_REPEATS)
    settings.putInt(CURRENT_REPEATS, savedValue)
    return savedValue
}

fun getRepeats() =
    settings.getInt(CURRENT_REPEATS, DEFAULT_REPEATS).coerceIn(1, MAX_REPEATS)

fun savePause(pause: Long): Long {
    val savedValue = pause.coerceIn(MIN_PAUSE, MAX_PAUSE)
    settings.putLong(PAUSE_AFTER_EACH, savedValue)
    return savedValue
}

fun getPause() =
    settings.getLong(PAUSE_AFTER_EACH, DEFAULT_PAUSE)

fun isTutorialCompleted(): Boolean {
    val result = settings.getBoolean(TUTORIAL_COMPLETED_KEY)
    Napier.d("settings.isTutorialCompleted() = $result", tag = "TUTOR")
    return result
}

fun setTutorialCompleted() {
    Napier.d("setTutorialCompleted", tag = "TUTOR")
    settings.putBoolean(TUTORIAL_COMPLETED_KEY, true)
}

fun weekFromOrdinal(ordinal: Int) =
    Week.values().firstOrNull { it.ordinal == ordinal } ?: Week.FIRST
