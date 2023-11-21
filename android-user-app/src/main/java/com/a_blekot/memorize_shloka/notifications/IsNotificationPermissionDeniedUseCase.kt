package com.a_blekot.memorize_shloka.notifications

import android.os.Build
import com.a_blekot.memorize_shloka.permissions.Permission
import com.a_blekot.memorize_shloka.permissions.PermissionManager
import com.a_blekot.memorize_shloka.permissions.PermissionResult
import com.a_blekot.shlokas.common.utils.notificationPermissionWasRequested
import javax.inject.Inject

class IsNotificationPermissionDeniedUseCase @Inject constructor() {

    operator fun invoke(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return false
        }
        val permissionResult = PermissionManager.checkPermission(
            permission = Permission.Notification,
            wasPermissionRequestedBefore = notificationPermissionWasRequested,
        )
        return permissionResult == PermissionResult.Denied
    }
}
