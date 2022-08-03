package com.a_blekot.shlokas.android

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.a_blekot.shlokas.android.MainApp.Companion.app
import com.a_blekot.shlokas.android.player.PlaybackService
import com.a_blekot.shlokas.android.theme.AppTheme
import com.a_blekot.shlokas.common.root.RootComponent
import com.a_blekot.shlokas.common.root.RootComponentImpl
import com.a_blekot.shlokas.common.root.RootDeps
import com.a_blekot.shlokas.common.utils.AndroidFiler
import com.a_blekot.shlokas.common.utils.dispatchers
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            playbackService = (binder as? PlaybackService.PlaybackBinder)?.service
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

        Napier.d( "ACTIVITY startService", tag = "PlaybackService")
        startService(playbackServiceIntent)
        lifecycleScope.launchWhenCreated {
            delay(500L)
            if (applicationContext.bindService(playbackServiceIntent, serviceConnection, Context.BIND_IMPORTANT)) {
                isBound = true
            }
        }
    }

    override fun onStart() {
        playbackService?.onActivityStarted()
        super.onStart()
    }

    override fun onStop() {
        playbackService?.onActivityStopped()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService()
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
                playerBus = app.playerBus,
                dispatchers = dispatchers()
            )
        )
}
