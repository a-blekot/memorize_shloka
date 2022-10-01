package com.a_blekot.shlokas.android_player

import android.Manifest.permission.WAKE_LOCK
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import com.a_blekot.shlokas.common.utils.cancelSafely
import com.a_blekot.shlokas.common.player_api.PlayerBus
import com.a_blekot.shlokas.common.utils.LogTag.PLAYBACK_SERVICE
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*

private const val STOP_PLAYER_DELAY_MS = 10_000L

class PlaybackService : Service(), Player.Listener {

    inner class PlaybackBinder : Binder() {
        val service: PlaybackService
            get() = this@PlaybackService
    }

    private val binder = PlaybackBinder()
    private var player: Player? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var isActivityStarted = false

    private val playerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun onActivityStarted() {
        isActivityStarted = true
        Napier.d("onActivityStarted", tag = PLAYBACK_SERVICE.name)
        stopForeground(true)
        Napier.d("stopForeground", tag = PLAYBACK_SERVICE.name)
        player?.hideNotification()
    }

    fun onActivityStopped() =
        player?.run {
            isActivityStarted = false
            Napier.d("onActivityStopped", tag = PLAYBACK_SERVICE.name)
            Napier.d("isPlaying = $isPlaying", tag = PLAYBACK_SERVICE.name)

            if (isPlaying) {
                showNotification()
            } else {
                playerScope.launch {
                    delay(STOP_PLAYER_DELAY_MS)
                    stop()
                }
            }
        }

    fun setPlayerBus(playerBus: PlayerBus) =
        player?.setPlayerBuss(playerBus)

    override fun onBind(intent: Intent): IBinder {
        Napier.d("onBind", tag = PLAYBACK_SERVICE.name)
        return binder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Napier.d("onStartCommand", tag = PLAYBACK_SERVICE.name)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Napier.d("onCreate", tag = PLAYBACK_SERVICE.name)
        player = Player(this, playerScope, this)

        requireWakeLock()
    }

    override fun onDestroy() {
        Napier.d("onDestroy", tag = PLAYBACK_SERVICE.name)
        releaseWakeLock()
        playerScope.cancelSafely("Service.onDestroy")
        super.onDestroy()
    }

    override fun onNotificationPosted(notification: Notification) {
        Napier.d("startForeground notification = $notification", tag = PLAYBACK_SERVICE.name)
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onNotificationCancelled() {
        Napier.d("onNotificationCancelled", tag = PLAYBACK_SERVICE.name)
        stop()
    }

    private fun stop() {
        if (isActivityStarted) {
            return
        }
        Napier.d("stop", tag = PLAYBACK_SERVICE.name)
        stopForeground(true)
        Napier.d("stopForeground", tag = PLAYBACK_SERVICE.name)

        player?.release()
        player = null
        stopSelf()
        Napier.d("stopSelf", tag = PLAYBACK_SERVICE.name)
    }

    // 1_800_000ms = 30 min
    private fun requireWakeLock(duration: Long = 1_800_000) {
        if (packageManager.checkPermission(WAKE_LOCK, packageName) == PERMISSION_GRANTED) {
            try {
                val manager = getSystemService(POWER_SERVICE) as? PowerManager
                wakeLock = manager?.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, application.packageName)
                wakeLock?.acquire(duration)
                Napier.d("acquireWakeLock", tag = PLAYBACK_SERVICE.name)
            } catch (e: Throwable) {
                Napier.e("acquireWakeLock error", e, tag = PLAYBACK_SERVICE.name)
                /** do nothing */
            }
        }
    }

    private fun releaseWakeLock() =
        wakeLock?.apply {
            Napier.d("releaseWakeLock", tag = PLAYBACK_SERVICE.name)
            if (isHeld) release()
        }
}
