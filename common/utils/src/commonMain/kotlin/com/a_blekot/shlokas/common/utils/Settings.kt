package com.a_blekot.shlokas.common.utils

import com.a_blekot.shlokas.common.data.Week
import com.russhwolf.settings.Settings

private const val APP_LAUNCH_COUNT = "APP_LAUNCH_COUNT"
private const val CURRENT_REPEATS = "CURRENT_REPEATS"
private const val CURRENT_WEEK = "CURRENT_WEEK"
private const val LAST_CONFIG_ID_KEY = "LAST_CONFIG_NAME_KEY"
private const val PAUSE_AFTER_EACH = "PAUSE_AFTER_EACH"

private const val DEFAULT_REPEATS = 10
private const val MAX_REPEATS = 10
private const val DEFAULT_PAUSE = 500L

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

fun saveCurrentWeek(week: Week) =
    settings.putInt(CURRENT_WEEK, week.ordinal)

fun getCurrentWeek() =
    settings.getInt(CURRENT_WEEK).let {
        weekFromOrdinal(it)
    }

fun saveRepeats(repeats: Int) =
    settings.putInt(CURRENT_REPEATS, repeats.coerceIn(1, MAX_REPEATS))

fun getRepeats() =
    settings.getInt(CURRENT_REPEATS, DEFAULT_REPEATS).coerceIn(1, MAX_REPEATS)

fun savePause(pause: Long) =
    settings.putLong(PAUSE_AFTER_EACH, pause)

fun getPause() =
    settings.getLong(PAUSE_AFTER_EACH, DEFAULT_PAUSE)

fun weekFromOrdinal(ordinal: Int) =
    Week.values().firstOrNull { it.ordinal == ordinal } ?: Week.FIRST
