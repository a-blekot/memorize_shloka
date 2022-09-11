package com.a_blekot.shlokas.common.utils.analytics

import com.a_blekot.shlokas.common.utils.analytics.AnalyticsEvent.TUTORIAL_OPEN
import com.a_blekot.shlokas.common.utils.analytics.AnalyticsParam.*
import com.a_blekot.shlokas.common.utils.getAppLaunchCount
import com.a_blekot.shlokas.common.utils.getPreRatingClosedCount
import com.a_blekot.shlokas.common.utils.getPreRatingShownCount
import com.a_blekot.shlokas.common.utils.getTutorialSkippCount

interface Analytics {
    fun logEvent(event: AnalyticsEvent, params: Map<String, Any>? = null)
}

fun Analytics.playList(listId: String, count: Int, repeats: Int) =
    logEvent(
        AnalyticsEvent.PLAY_LIST,
        mapOf(
            LIST_ID.low to listId,
            COUNT.low to count,
            REPEATS.low to repeats,
        )
    )

fun Analytics.playShloka(shlokaId: String, repeats: Int) =
    logEvent(
        AnalyticsEvent.PLAY_LIST,
        mapOf(
            SHLOKA_ID.low to shlokaId,
            REPEATS.low to repeats,
        )
    )

fun Analytics.playCompleted(count: Int, repeats: Int, durationSec: Long) =
    logEvent(
        AnalyticsEvent.PLAY_COMPLETED,
        mapOf(
            COUNT.low to count,
            REPEATS.low to repeats,
            DURATION.low to durationSec
        )
    )

fun Analytics.preratingShown() =
    logEvent(
        AnalyticsEvent.PRERATING_SHOWN,
        mapOf(COUNT.low to getPreRatingShownCount())
    )

fun Analytics.preratingAccepted() =
    logEvent(
        AnalyticsEvent.PRERATING_ACCEPTED,
    )

fun Analytics.preratingClosed() =
    logEvent(
        AnalyticsEvent.PRERATING_CLOSED,
        mapOf(COUNT.low to getPreRatingClosedCount())
    )

fun Analytics.tutorialOpen() =
    logEvent(
        TUTORIAL_OPEN,
        mapOf(LAUNCH_COUNT.low to getAppLaunchCount())
    )

fun Analytics.tutorialSettings() =
    logEvent(AnalyticsEvent.TUTORIAL_SETTINGS)

fun Analytics.tutorialSkip() =
    logEvent(
        AnalyticsEvent.TUTORIAL_SKIP,
        mapOf(COUNT.low to getTutorialSkippCount())
    )

fun Analytics.tutorialComplete(screen: AnalyticsScreen) =
    logEvent(
        AnalyticsEvent.TUTORIAL_COMPLETE,
        mapOf(SCREEN.low to screen.name)
    )
