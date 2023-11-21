package com.a_blekot.shlokas.common.utils

import com.a_blekot.shlokas.common.data.ShlokaId
import com.a_blekot.shlokas.common.data.RepeatMode
import com.a_blekot.shlokas.common.data.toListType
import com.russhwolf.settings.*
import com.russhwolf.settings.int

private const val APP_LAUNCH_COUNT = "APP_LAUNCH_COUNT"
private const val AUTOPLAY_KEY = "AUTOPLAY_KEY"
private const val CURRENT_REPEATS = "CURRENT_REPEATS"
private const val REPEAT_MODE = "CURRENT_WEEK"
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
private const val AUDIO_SPEED = "AUDIO_SPEED"
private const val AUDIO_PITCH = "AUDIO_PITCH"
private const val NOTIFICATION_PERMISSION_WAS_REQUESTED =
    "NOTIFICATION_PERMISSION_WAS_REQUESTED"
private const val NOTIFICATION_PERMISSION_WAS_ASKED =
    "NOTIFICATION_PERMISSION_WAS_ASKED"

private const val DEFAULT_REPEATS = 10
private const val MAX_REPEATS = 16_108

private const val MIN_PAUSE = 100L
private const val DEFAULT_PAUSE = 500L
private const val MAX_PAUSE = 10_000L

object Audio {
    object Speed {
        const val DEFAULT = 1.0f
        const val MIN = 0.25f
        const val MAX = 2.5f
    }

    object Pitch {
        const val DEFAULT = 1.0f
        const val MIN = 0.25f
        const val MAX = 2.5f
    }
}

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

var repeatMode: RepeatMode
    get() = RepeatMode.fromOrdinal(settings.getInt(REPEAT_MODE, 0))
    set(value) = settings.putInt(REPEAT_MODE, value.ordinal)

var locale: String by settings.string(LOCALE_KEY, "")

var repeats: Int by settings.int(CURRENT_REPEATS, DEFAULT_REPEATS, 1, MAX_REPEATS)
var pause: Long by settings.long(PAUSE_AFTER_EACH, DEFAULT_PAUSE, MIN_PAUSE, MAX_PAUSE)
var audioSpeed: Float by settings.float(AUDIO_SPEED, Audio.Speed.DEFAULT, Audio.Speed.MIN, Audio.Speed.MAX)
var audioPitch: Float by settings.float(AUDIO_PITCH, Audio.Pitch.DEFAULT, Audio.Pitch.MIN, Audio.Pitch.MAX)

var withSanskrit: Boolean by settings.boolean(WITH_SANSKRIT, true)
var withTranslation: Boolean by settings.boolean(WITH_TRANSLATION, true)

var isTutorialCompleted: Boolean by settings.boolean(TUTORIAL_COMPLETED_KEY, true)

var tutorialSkippCount: Int by settings.int(TUTORIAL_SKIPP_COUNT_KEY, 0)

fun onTutorialSkipped() =
    tutorialSkippCount.let { tutorialSkippCount = it + 1 }

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

/** In system dialog */
var notificationPermissionWasRequested by settings.boolean(NOTIFICATION_PERMISSION_WAS_REQUESTED, false)

/** In app dialog */
var notificationPermissionWasAsked by settings.boolean(NOTIFICATION_PERMISSION_WAS_ASKED, false)
