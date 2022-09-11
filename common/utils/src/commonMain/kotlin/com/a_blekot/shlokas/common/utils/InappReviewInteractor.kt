package com.a_blekot.shlokas.common.utils

private var wasShownInCurrentSession: Boolean = false
private var playCompletedCount = playCompletedCount()

object InappReviewInteractor {
    fun check(showDialog: () -> Unit) {
        when {
            wasShownInCurrentSession -> return
            inappReviewShown() -> return
            onAppLaunch() -> show(showDialog)
            onPlayCompleted() -> show(showDialog)
        }
    }

    private fun onAppLaunch() =
        getAppLaunchCount() in 3..4

    private fun onPlayCompleted() =
        playCompletedCount < playCompletedCount() && playCompletedCount() in 2..3

    private fun show(showDialog: () -> Unit) {
        wasShownInCurrentSession = true
        showDialog.invoke()
    }
}