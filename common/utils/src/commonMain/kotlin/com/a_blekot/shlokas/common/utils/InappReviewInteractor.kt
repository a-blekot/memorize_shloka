package com.a_blekot.shlokas.common.utils

private var wasShownInCurrentSession: Boolean = false
private var playCompleted = playCompletedCount

object InappReviewInteractor {
    fun check(showDialog: () -> Unit) {
        when {
            wasShownInCurrentSession -> return
            inappReviewShown -> return
            onAppLaunch() -> show(showDialog)
            onPlayCompleted() -> show(showDialog)
        }
    }

    private fun onAppLaunch() =
        appLaunchCount % 5 == 0

    private fun onPlayCompleted() =
        playCompleted < playCompletedCount && playCompletedCount in 2..3

    private fun show(showDialog: () -> Unit) {
        wasShownInCurrentSession = true
        showDialog.invoke()
    }
}