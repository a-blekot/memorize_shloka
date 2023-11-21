package com.a_blekot.memorize_shloka.notifications

import android.os.Build
import com.a_blekot.memorize_shloka.permissions.Permission
import com.a_blekot.memorize_shloka.permissions.PermissionManager
import com.a_blekot.shlokas.common.utils.notificationPermissionWasRequested

class RequestNotificationPermissionUseCase() {

    suspend operator fun invoke() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionWasRequested = true
            PermissionManager.requestPermission(Permission.Notification)
        } else {
            throw IllegalStateException("No notification permission requests on Android SDK ${Build.VERSION.SDK_INT}")
        }
    }
}
