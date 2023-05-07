package com.a_blekot.shlokas.android_player

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.a_blekot.shlokas.common.data.tasks.*
import com.a_blekot.shlokas.common.player_api.PlayerBus
import com.a_blekot.shlokas.common.player_api.PlayerFeedback
import com.a_blekot.shlokas.common.utils.locale
import com.a_blekot.shlokas.common.utils.resources.getAssetPath
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import io.github.aakira.napier.Napier
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


private const val ASSETS_PREFIX = "asset:///"
private const val CHANNEL_ID = "SHLOKA_CHANNEL_16108"
const val NOTIFICATION_ID = 16108

class Player(
    context: Context,
    private val playerScope: CoroutineScope,
    private val listener: Listener
) {

    interface Listener {
        fun onNotificationPosted(notification: Notification) {}
        fun onNotificationCancelled() {}
    }

    private var currentTask: Task = StopTask

    private val Int.readablePlaybackState
        get() = when (this) {
            Player.STATE_IDLE -> "Player.STATE_IDLE"
            Player.STATE_BUFFERING -> "Player.STATE_BUFFERING"
            Player.STATE_READY -> "Player.STATE_READY"
            Player.STATE_ENDED -> "Player.STATE_ENDED"
            else -> "wrong state $this. Should be in range ${Player.STATE_IDLE}..${Player.STATE_ENDED}"
        }

    private val playbackListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            Napier.w("onPlaybackStateChanged ${playbackState.readablePlaybackState}", tag = "AUDIO_PLAYER")
            when (playbackState) {
                Player.STATE_READY -> onPlaybackReady()
                else -> {
                    /** do nothing */
                }
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying) {
                onPlaybackStarted()
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            Napier.e("onPlayerError", error, tag = "AUDIO_PLAYER")
        }
    }

    private val ttsListener = object : UtteranceProgressListener() {
        override fun onStart(id: String?) {}
        override fun onDone(id: String?) = onTtsComplete(id)
        override fun onError(id: String?) = onTtsComplete(id)
    }

    private val ttsInitListener = TextToSpeech.OnInitListener {
        when (it) {
            TextToSpeech.SUCCESS -> {
                tts?.language = Locale.getDefault()
                Napier.d("init SUCCESS ${Locale.getDefault()}", tag = "TTS")
            }
            TextToSpeech.ERROR -> {
                shutDownTts()
                Napier.d("init ERROR", tag = "TTS")
            }
        }
    }


    private var tts: TextToSpeech? = TextToSpeech(context, ttsInitListener)

    private var exoPlayer: ExoPlayer? = ExoPlayer.Builder(context)
        .build().apply {
            repeatMode = Player.REPEAT_MODE_OFF
            addListener(playbackListener)
        }

    private val mediaDescriptionAdapter by lazy {
        object : PlayerNotificationManager.MediaDescriptionAdapter {
            override fun createCurrentContentIntent(player: Player): PendingIntent? {
//                val intent = Intent(context, MainActivity::class.java)
//
//                val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//                return PendingIntent.getActivity(context, 0, intent, flags)
                return null
            }

            override fun getCurrentContentText(player: Player) =
                exoPlayer?.mediaMetadata?.description.toString()

            override fun getCurrentContentTitle(player: Player) =
                exoPlayer?.mediaMetadata?.title.toString()

            override fun getCurrentLargeIcon(player: Player, callback: PlayerNotificationManager.BitmapCallback) =
                notificationBg
        }
    }

    private val notificationBg: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.prabhupada_speaking)

    private val notificationListener by lazy {
        object : PlayerNotificationManager.NotificationListener {
            override fun onNotificationPosted(notificationId: Int, notification: Notification, onGoing: Boolean) {
                Napier.d("onNotificationPosted = $notificationId", tag = "AUDIO_PLAYER")
                listener.onNotificationPosted(notification)
            }

            override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                Napier.d("onNotificationCancelled", tag = "AUDIO_PLAYER")
                listener.onNotificationCancelled()
            }
        }
    }

    private val playerNotificationManager by lazy {
        PlayerNotificationManager.Builder(context, NOTIFICATION_ID, CHANNEL_ID)
            .setSmallIconResourceId(R.drawable.aum)
            .setStopActionIconResourceId(R.drawable.ic_player_close)
            .setPlayActionIconResourceId(R.drawable.ic_player_play)
            .setPauseActionIconResourceId(R.drawable.ic_player_pause)
            .setChannelNameResourceId(R.string.playback_channel_name)
            .setMediaDescriptionAdapter(mediaDescriptionAdapter)
            .setNotificationListener(notificationListener)
            .build().apply {
                setUseStopAction(true)
                setUsePlayPauseActions(true)
                setUseNextAction(true)
                setUsePreviousAction(false)
                setUseFastForwardAction(true)
                setUseRewindAction(true)
                setUseFastForwardActionInCompactView(true)
                setUseRewindActionInCompactView(true)
                setUseChronometer(true)
                setColorized(true)
                setColor(notificationColor)
            }
    }

    val isPlaying: Boolean
        get() {
            return exoPlayer?.isPlaying == true || currentTask !is StopTask
        }

    lateinit var playerBus: PlayerBus

    fun setPlayerBuss(playerBus: PlayerBus) {
        if (!this::playerBus.isInitialized || this.playerBus != playerBus) {
            this.playerBus = playerBus
            playerBus.observeTasks(::handleTask, playerScope)
            Napier.d("Player::SET playerBus = $playerBus", tag = "PlayerBus")
        } else {
            Napier.d("Player::SAME playerBus = ${this.playerBus}", tag = "PlayerBus")
        }
    }

    fun release() {
        hideNotification()
        exoPlayer?.release()
        exoPlayer = null
    }

    private fun shutDownTts() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }

    fun showNotification() {
        Napier.d("showNotification", tag = "AUDIO_PLAYER")
        playerNotificationManager.setPlayer(exoPlayer)
    }

    fun hideNotification() {
        Napier.d("hideNotification", tag = "AUDIO_PLAYER")
        playerNotificationManager.setPlayer(null)
    }

    private fun handleTask(task: Task) {
        Napier.d("handleTask $task", tag = "AUDIO_PLAYER")
        currentTask = task
        when (task) {
            is SetTrackTask -> setTrack(task)
            is PlayTask -> play(task)
            is PlayTranslationTask -> playTranslation(task)
            is PauseTask, IdleTask, NoAudioTask -> pause()
            is StopTask -> stop()
            is ResetCounterTask -> {}
        }
    }

    private fun setTrack(task: SetTrackTask) =
        exoPlayer?.apply {
            playWhenReady = false
            setMediaItem(task.toMediaItem())
            prepare()
        }

    private fun pause() {
        exoPlayer?.playWhenReady = false
        pauseTts()
    }

    private fun pauseTts() =
        tts?.run {
            setOnUtteranceProgressListener(null)
            stop()
        }

    private fun stop() {
        pause()
        exoPlayer?.clearMediaItems()
    }

    private fun play(task: PlayTask) =
        exoPlayer?.run {
            playWhenReady = false
            try {
                exoPlayer?.seekTo(task.startMs)
            } catch (e: IllegalSeekPositionException) {
                Napier.e("play $task", tag = "AUDIO_PLAYER", throwable = e)
            }
            Napier.d("play $task", tag = "AUDIO_PLAYER")
            playWhenReady = true
        }

    private fun playTranslation(task: PlayTranslationTask) =
        task.run {
            if (ttsIsNotAvailable(locale)) {
                onTtsComplete(task.id.name)
                return@run
            }

            pause()
            tts?.run {
                language = Locale(locale)
                setSpeechRate(SPEECH_RATE)
                setPitch(1.0f)
                setOnUtteranceProgressListener(ttsListener)
                speak(text, TextToSpeech.QUEUE_FLUSH, null, id.name)
            }
        }

    private fun onPlaybackReady() {
        Napier.d("onPlaybackReady", tag = "AUDIO_PLAYER")
        if (currentTask is SetTrackTask) {
            Napier.d("PlayerFeedback.Ready", tag = "AUDIO_PLAYER")
            playerBus.update(PlayerFeedback.Ready)
        }
    }

    private fun onPlaybackStarted() {
        Napier.d("onPlaybackStarted", tag = "AUDIO_PLAYER")
        (currentTask as? PlayTask)?.run {
            playerScope.launch {
                Napier.d("PlayerFeedback.Started (duration = ${duration})", tag = "AUDIO_PLAYER")
                playerBus.update(PlayerFeedback.Started(duration))
            }
        }
    }

    private fun onTtsComplete(id: String?) {
        Napier.d("onTtsComplete", tag = "AUDIO_PLAYER")
        val currentId = (currentTask as? PlayTranslationTask)?.id ?: return

        if (currentId.name == id) {
            Napier.d("PlayerFeedback.Ready", tag = "AUDIO_PLAYER")
            playerBus.update(PlayerFeedback.Ready)
        }
    }

    private fun ttsIsNotAvailable(locale: String) =
        tts?.availableLanguages?.any { it.language == locale } != true

    private fun SetTrackTask.toMediaItem(): MediaItem {
        val uri = Uri.parse("$ASSETS_PREFIX${getAssetPath(id)}")
        Napier.d("SetTrack $uri", tag = "AUDIO_PLAYER")

        return MediaItem.Builder()
            .setUri(uri)
            .setMediaMetadata(toMetaData())
            .build()
    }

    private fun SetTrackTask.toMetaData() =
        MediaMetadata.Builder()
            .setDescription(description)
            .setTitle(title)
            .build()
}
