package com.a_blekot.shlokas.common.utils

import com.a_blekot.shlokas.common.data.ShlokaId
import com.a_blekot.shlokas.common.data.Week
import com.a_blekot.shlokas.common.data.toListType
import com.a_blekot.shlokas.common.utils.IntDelegate
import com.russhwolf.settings.*
import com.russhwolf.settings.int
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private const val APP_LAUNCH_COUNT = "APP_LAUNCH_COUNT"
private const val AUTOPLAY_KEY = "AUTOPLAY_KEY"
private const val CURRENT_REPEATS = "CURRENT_REPEATS"
private const val CURRENT_WEEK = "CURRENT_WEEK"
private const val INAPP_REVIEW_SHOWN = "INAPP_REVIEW_SHOWN"
private const val LAST_CONFIG_ID_KEY = "LAST_CONFIG_NAME_KEY"
private const val LOCALE_KEY = "LOCALE_KEY"
private const val PAUSE_AFTER_EACH = "PAUSE_AFTER_EACH"
private const val PLAY_COMPLETED_COUNT = "PLAY_COMPLETED_COUNT"
private const val PRERATING_CLOSED_COUNT = "PRERATING_CLOSED_COUNT"
private const val PRERATING_SHOWN_COUNT = "PRERATING_SHOWN_COUNT"
private const val SHLOKA_SELECTED_KEY = "SHLOKA_SELECTED_KEY"
private const val TUTORIAL_COMPLETED_KEY = "TUTORIAL_COMPLETED_KEY"
private const val TUTORIAL_SKIPP_COUNT_KEY = "TUTORIAL_SKIPP_COUNT_KEY"
private const val SHOW_CLOSE_PLAYER_DIALOG = "SHOW_CLOSE_PLAYER_DIALOG"
private const val ALLOW_SWIPE_ON_PLAYER = "ALLOW_SWIPE_ON_PLAYER"
private const val WITH_SANSKRIT = "WITH_SANSKRIT"
private const val WITH_TRANSLATION = "WITH_TRANSLATION"

private const val DEFAULT_REPEATS = 10
private const val MAX_REPEATS = 16_108

private const val MIN_PAUSE = 100L
private const val DEFAULT_PAUSE = 500L
private const val MAX_PAUSE = 10_000L

private val settings = Settings()

var tutorialWasShownInThisSession = false

var appLaunchCount: Int by settings.int(APP_LAUNCH_COUNT, 0)

fun onAppLaunch() =
    appLaunchCount.let { appLaunchCount = it + 1 }

var inappReviewShown: Boolean by settings.boolean(INAPP_REVIEW_SHOWN, false)

var playCompletedCount: Int by settings.int(PLAY_COMPLETED_COUNT, 0)

fun onPlayCompleted() =
    playCompletedCount.let {
        playCompletedCount = it + 1
    }

fun saveLastListId(id: String) =
    settings.putString(LAST_CONFIG_ID_KEY, id)

fun getLastListId() =
    settings.getString(LAST_CONFIG_ID_KEY, "").toListType()

var autoPlay: Boolean by settings.boolean(AUTOPLAY_KEY, false)

var currentWeek: Week
    get() = weekFromOrdinal(settings.getInt(CURRENT_WEEK, 0))
    set(value) = settings.putInt(CURRENT_WEEK, value.ordinal)

var locale: String by settings.string(LOCALE_KEY, "")

var repeats: Int by settings.int(CURRENT_REPEATS, DEFAULT_REPEATS, 1, MAX_REPEATS)
var pause: Long by settings.long(PAUSE_AFTER_EACH, DEFAULT_PAUSE, MIN_PAUSE, MAX_PAUSE)

var withSanskrit: Boolean by settings.boolean(WITH_SANSKRIT, true)
var withTranslation: Boolean by settings.boolean(WITH_TRANSLATION, true)

var isTutorialCompleted: Boolean by settings.boolean(TUTORIAL_COMPLETED_KEY, true)

var tutorialSkippCount: Int by settings.int(TUTORIAL_SKIPP_COUNT_KEY, 0)

fun onTutorialSkipped() =
    tutorialSkippCount.let { tutorialSkippCount = it + 1 }

fun weekFromOrdinal(ordinal: Int) =
    Week.values().firstOrNull { it.ordinal == ordinal } ?: Week.FIRST

fun selectShloka(id: ShlokaId, isSelected: Boolean) =
    settings.putBoolean("$SHLOKA_SELECTED_KEY-${id.id}", isSelected)

fun isSelected(id: ShlokaId) =
    settings.getBoolean("$SHLOKA_SELECTED_KEY-${id.id}", true)

var preRatingShownCount: Int by settings.int(PRERATING_SHOWN_COUNT, 0)
var preRatingClosedCount: Int by settings.int(PRERATING_CLOSED_COUNT, 0)

fun onPreRatingShown() =
    preRatingShownCount.let { preRatingShownCount = it + 1 }

fun onPreRatingClosed() =
    preRatingClosedCount.let { preRatingClosedCount = it + 1 }

var showClosePlayerDialog: Boolean by settings.boolean(SHOW_CLOSE_PLAYER_DIALOG, true)
var allowSwipeOnPlayer: Boolean by settings.boolean(ALLOW_SWIPE_ON_PLAYER, true)

fun Settings.int(
    key: String? = null,
    defaultValue: Int,
    minValue: Int,
    maxValue: Int
): ReadWriteProperty<Any?, Int> =
    IntDelegate(
        settings = this,
        key = key,
        defaultValue = defaultValue,
        minValue = minValue,
        maxValue = maxValue,
    )

fun Settings.long(
    key: String? = null,
    defaultValue: Long,
    minValue: Long,
    maxValue: Long
): ReadWriteProperty<Any?, Long> =
    LongDelegate(
        settings = this,
        key = key,
        defaultValue = defaultValue,
        minValue = minValue,
        maxValue = maxValue,
    )

private class IntDelegate(
    private val settings: Settings,
    key: String?,
    private val defaultValue: Int,
    private val minValue: Int,
    private val maxValue: Int,
) : OptionalKeyDelegate<Int>(key) {
    override fun getValue(key: String): Int =
        settings[key, defaultValue].coerceIn(minValue, maxValue)

    override fun setValue(key: String, value: Int) {
        settings[key] = value.coerceIn(minValue, maxValue)
    }
}

private class LongDelegate(
    private val settings: Settings,
    key: String?,
    private val defaultValue: Long,
    private val minValue: Long,
    private val maxValue: Long,
) : OptionalKeyDelegate<Long>(key) {
    override fun getValue(key: String): Long =
        settings[key, defaultValue].coerceIn(minValue, maxValue)

    override fun setValue(key: String, value: Long) {
        settings[key] = value.coerceIn(minValue, maxValue)
    }
}

private abstract class OptionalKeyDelegate<T>(private val key: String?) : ReadWriteProperty<Any?, T> {

    abstract fun getValue(key: String): T
    abstract fun setValue(key: String, value: T)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = getValue(key ?: property.name)
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setValue(key ?: property.name, value)
    }
}