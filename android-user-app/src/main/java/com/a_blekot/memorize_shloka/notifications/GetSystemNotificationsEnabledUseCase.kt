package com.a_blekot.memorize_shloka.notifications

import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat

class GetSystemNotificationsEnabledUseCase(
    private val systemNotificationsManager: SystemNotificationsManager,
) {

    operator fun invoke() = when {
        systemNotificationsManager.notificationManager.areNotificationsEnabled().not() -> false
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ->
            haveNoBlockedChannelGroups(systemNotificationsManager.notificationManager)
        else -> true
    }

    private fun haveNoBlockedChannelGroups(notificationManager: NotificationManagerCompat) =
        notificationManager.notificationChannels.firstOrNull { channel ->
            val channelGroup =
                channel.group?.let { notificationManager.getNotificationChannelGroup(it) }
            val groupBlocked = channelGroup != null && channelGroup.isBlocked
            groupBlocked || channel.importance == NotificationManager.IMPORTANCE_NONE
        } == null
}
