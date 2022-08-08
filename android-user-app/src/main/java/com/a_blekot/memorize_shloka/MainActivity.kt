package com.a_blekot.memorize_shloka

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.a_blekot.memorize_shloka.MainApp.Companion.app
import com.a_blekot.shlokas.android_player.PlaybackService
import com.a_blekot.shlokas.android_ui.theme.AppTheme
import com.a_blekot.shlokas.common.root.RootComponent
import com.a_blekot.shlokas.common.root.RootComponentImpl
import com.a_blekot.shlokas.common.root.RootDeps
import com.a_blekot.shlokas.common.utils.resources.AndroidConfigReader
import com.a_blekot.shlokas.common.utils.AndroidFiler
import com.a_blekot.shlokas.common.utils.dispatchers
import com.a_blekot.shlokas.common.utils.resources.AndroidStringResourceHandler
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            playbackService = (binder as? PlaybackService.PlaybackBinder)?.service
            playbackService?.setPlayerBus(app.playerBus)
            playbackService?.onActivityStarted()
            Napier.d( "ACTIVITY onServiceConnected", tag = "PlaybackService")
            Napier.d( "ACTIVITY boundService = $playbackService", tag = "PlaybackService")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            playbackService = null
            Napier.d( "ACTIVITY onServiceDisconnected", tag = "PlaybackService")
            Napier.d( "ACTIVITY boundService = $playbackService", tag = "PlaybackService")
        }
    }
    private val playbackServiceIntent
        get() = Intent(this, PlaybackService::class.java)

    private var isBound = false
    private var playbackService: PlaybackService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = root(defaultComponentContext())

        setContent {
            AppTheme {
                MainContent(root)
            }
        }
        bindService()
    }

    override fun onStart() {
        super.onStart()
        Napier.d( "ACTIVITY startService", tag = "PlaybackService")
        startService(playbackServiceIntent)
    }

    override fun onStop() {
        playbackService?.onActivityStopped()
        unbindService()
        super.onStop()
    }

    private fun bindService() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500L)
                Napier.d("app.playerBus = ${app.playerBus}", tag="PlayerBus")
                if (applicationContext.bindService(playbackServiceIntent, serviceConnection, Context.BIND_IMPORTANT)) {
                    isBound = true
                }
            }
        }
    }

    private fun unbindService() {
        if (isBound) {
            applicationContext.unbindService(serviceConnection)
            isBound = false
        }
    }

    private fun root(componentContext: ComponentContext): RootComponent =
        RootComponentImpl(
            componentContext = componentContext,
            storeFactory = LoggingStoreFactory(DefaultStoreFactory()),
            deps = RootDeps(
                filer = AndroidFiler(this),
                configReader = AndroidConfigReader(this),
                stringResourceHandler = AndroidStringResourceHandler(this),
                playerBus = app.playerBus,
                dispatchers = dispatchers(),
                onEmail = ::sendEmail
            )
        )

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            val uriText = "mailto:aleksey.blekot@gmail.com?subject=Шлоки - обратная связь&body=Харе Кришна! Спасибо за приложение \uD83D\uDE07"
            data = Uri.parse(uriText)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Napier.e("No activity for $intent")
        }
    }
}
