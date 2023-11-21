package com.a_blekot.memorize_shloka.notifications

import android.content.Context
import androidx.core.app.NotificationManagerCompat

class SystemNotificationsManager(
    private val appContext: Context,
) {
    val notificationManager by lazy { NotificationManagerCompat.from(appContext) }
}
