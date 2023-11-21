package com.a_blekot.memorize_shloka.notifications

import android.os.Build
import com.a_blekot.shlokas.common.utils.notificationPermissionWasAsked
import javax.inject.Inject

class ShouldShowNotificationPermissionDialogUseCase @Inject constructor(
    private val isNotificationPermissionDenied: IsNotificationPermissionDeniedUseCase,
) {

    operator fun invoke(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return false
        }
        if (notificationPermissionWasAsked) {
            return false
        }
        val shouldShow = isNotificationPermissionDenied()
        if (shouldShow) {
            notificationPermissionWasAsked = true
        }
        return shouldShow
    }
}
