package com.a_blekot.shlokas.android_player

import android.app.PendingIntent

interface PendingIntentProvider {
    operator fun invoke(): PendingIntent
}