package com.a_blekot.shlokas.common.utils

import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.data.Week
import com.russhwolf.settings.Settings

private const val CURRENT_WEEK = "CURRENT_WEEK"
private const val CURRENT_REPEATS = "CURRENT_REPEATS"
private const val PAUSE_AFTER_EACH = "PAUSE_AFTER_EACH"
private const val LAST_LIST_FILE_NAME_KEY = "LAST_LIST_FILE_NAME_KEY"

private const val DEFAULT_REPEATS = 10
private const val MAX_REPEATS = 10
private const val DEFAULT_PAUSE = 500L

private val settings = Settings()

fun saveLastListFileName(fileName: String) =
    settings.putString(LAST_LIST_FILE_NAME_KEY, fileName)

fun getLastListFileName() =
    settings.getString(LAST_LIST_FILE_NAME_KEY)

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
