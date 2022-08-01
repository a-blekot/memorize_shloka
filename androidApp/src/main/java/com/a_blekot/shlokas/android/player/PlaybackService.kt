package com.a_blekot.shlokas.android.player

import android.Manifest.permission.WAKE_LOCK
import android.app.Notification
import android.app.Service
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import androidx.core.content.ContextCompat.getSystemService
import com.a_blekot.shlokas.android.MainApp.Companion.app
import com.a_blekot.shlokas.common.utils.cancelSafely
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class PlaybackService : Service(), Player.Listener {

    inner class PlaybackBinder : Binder() {
        val service: PlaybackService
            get() = this@PlaybackService
    }

    private val binder = PlaybackBinder()
    private var player: Player? = null
    private var wakeLock: PowerManager.WakeLock? = null

    private val playerBus = app.playerBus
    private val playerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun onActivityStarted() {
        Napier.d("onActivityStarted", tag = "PlaybackService")
        stopForeground(true)
        Napier.d("stopForeground", tag = "PlaybackService")
        player?.hideNotification()
    }

    fun onActivityStopped() =
        player?.run {
            Napier.d("onActivityStopped", tag = "PlaybackService")
            Napier.d("isPlaying = $isPlaying", tag = "PlaybackService")

            if (isPlaying) {
                showNotification()
            } else {
                stop()
            }
        }

    override fun onBind(intent: Intent): IBinder {
        Napier.d("onBind", tag = "PlaybackService")
        return binder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Napier.d("onStartCommand", tag = "PlaybackService")
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Napier.d("onCreate", tag = "PlaybackService")
        player = Player(this, playerBus, playerScope, this)

        requireWakeLock()
    }

    override fun onDestroy() {
        Napier.d("onDestroy", tag = "PlaybackService")
        releaseWakeLock()
        playerScope.cancelSafely("Service.onDestroy")
        super.onDestroy()
    }

    override fun onNotificationPosted(notification: Notification) {
        Napier.d("startForeground notification = $notification", tag = "PlaybackService")
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onNotificationCancelled() {
        Napier.d("onNotificationCancelled", tag = "PlaybackService")
        stop()
    }

    private fun stop() {
        Napier.d("stop", tag = "PlaybackService")
        stopForeground(true)
        Napier.d("stopForeground", tag = "PlaybackService")

        player?.release()
        player = null
        stopSelf()
        Napier.d("stopSelf", tag = "PlaybackService")
    }

    // 900_000ms = 15 min
    private fun requireWakeLock(duration: Long = 900_000) {
        if (packageManager.checkPermission(WAKE_LOCK, packageName) == PERMISSION_GRANTED) {
            try {
                val manager = getSystemService(POWER_SERVICE) as? PowerManager
                wakeLock = manager?.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, application.packageName)
                wakeLock?.acquire(duration)
                Napier.d("acquireWakeLock", tag = "PlaybackService")
            } catch (e: Throwable) {
                Napier.e("acquireWakeLock error", e, tag = "PlaybackService")
                /** do nothing */
            }
        }
    }

    private fun releaseWakeLock() =
        wakeLock?.apply {
            Napier.d("releaseWakeLock", tag = "PlaybackService")
            if (isHeld) release()
        }
}
