package com.a_blekot.memorize_shloka

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.a_blekot.memorize_shloka.MainApp.Companion.app
import com.a_blekot.memorize_shloka.fragments.createScreenResultProvider
import com.a_blekot.memorize_shloka.inapp.BillingHelperAndroid
import com.a_blekot.memorize_shloka.inapp.InappUpdater
import com.a_blekot.memorize_shloka.inapp.showInappReview
import com.a_blekot.memorize_shloka.notifications.IsNotificationPermissionDeniedUseCase
import com.a_blekot.memorize_shloka.notifications.NotificationsPermissionDialogFragment
import com.a_blekot.memorize_shloka.notifications.RequestNotificationPermissionUseCase
import com.a_blekot.memorize_shloka.notifications.ShouldShowNotificationPermissionDialogUseCase
import com.a_blekot.memorize_shloka.permissions.PermissionManager
import com.a_blekot.shlokas.android_player.PendingIntentProvider
import com.a_blekot.shlokas.android_player.PlaybackService
import com.a_blekot.shlokas.android_ui.theme.AppTheme
import com.a_blekot.shlokas.common.data.PlatformApi
import com.a_blekot.shlokas.common.resources.MR.strings.email_body
import com.a_blekot.shlokas.common.resources.MR.strings.email_title
import com.a_blekot.shlokas.common.resources.resolve
import com.a_blekot.shlokas.common.utils.AndroidFiler
import com.a_blekot.shlokas.common.utils.LogTag.PLAYBACK_SERVICE
import com.a_blekot.shlokas.common.utils.billing.BillingHelper
import com.a_blekot.shlokas.common.utils.dispatchers.dispatchers
import com.a_blekot.shlokas.common.utils.resources.AndroidConfigReader
import com.a_blekot.shlokas.common.utils.resources.AndroidStringResourceHandler
import com.ablekot.shlokas.common.root.RootComponent
import com.ablekot.shlokas.common.root.RootComponentImpl
import com.ablekot.shlokas.common.root.RootDeps
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : FragmentActivity() {

    private val isNotificationPermissionDeniedUseCase = IsNotificationPermissionDeniedUseCase()
    private val requestNotificationPermission = RequestNotificationPermissionUseCase()
    private val shouldShowNotificationPermissionDialog = ShouldShowNotificationPermissionDialogUseCase(isNotificationPermissionDeniedUseCase)

    private val pendingIntentProvider = object : PendingIntentProvider {
        override fun invoke(): PendingIntent {
            val intent = Intent(app, MainActivity::class.java)
            val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            return PendingIntent.getActivity(app, 0, intent, flags)
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            playbackService = (binder as? PlaybackService.PlaybackBinder)?.service
            playbackService?.setPlayerBus(app.playerBus)
            playbackService?.setPendingIntentProvider(pendingIntentProvider)

            Napier.d("ACTIVITY onServiceConnected", tag = PLAYBACK_SERVICE.name)
            Napier.d("ACTIVITY boundService = $playbackService", tag = PLAYBACK_SERVICE.name)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            playbackService = null
            Napier.d("ACTIVITY onServiceDisconnected", tag = PLAYBACK_SERVICE.name)
            Napier.d("ACTIVITY boundService = $playbackService", tag = PLAYBACK_SERVICE.name)
        }
    }
    private val playbackServiceIntent
        get() = Intent(this, PlaybackService::class.java)

    private val platformApi = object: PlatformApi {
        override val hasTts = true
        override val hasInappReview = true

        override fun onEmail() = this@MainActivity.sendEmail()
        override fun onLink(link: String) = this@MainActivity.openLink(link)
        override fun onRateUs() = this@MainActivity.rateUs()
        override fun onShareApp() = this@MainActivity.shareApp()
        override fun onInappReview() = this@MainActivity.inappReview()
        override fun onSelectTtsVoice() = this@MainActivity.selectTtsVoice()
    }

    private val isBound
        get() = playbackService != null
    private var playbackService: PlaybackService? = null
    private var billingHelper: BillingHelper? = null
    private var inappUpdater: InappUpdater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addActivity()

        billingHelper = BillingHelperAndroid(
            activity = this,
            scope = this.lifecycleScope,
            dispatcherProvider = dispatchers(),
            connectivityObserver = app.connectivityObserver
        )
        inappUpdater = InappUpdater(this)

        val root = root(defaultComponentContext())

//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
//            TransparentSystemBars()
            AppTheme {
                MainContent(root)
            }
        }
        startAndBindService()
        app.connectivityObserver.start()
    }

    override fun onDestroy() {
        billingHelper?.clean()
        inappUpdater?.clean()
        app.connectivityObserver.stop()
        super.onDestroy()
        unbindService()
        removeActivity()
    }

    override fun onStart() {
        if (!isBound) {
            startAndBindService {
                playbackService?.onActivityStarted()
            }
        } else {
            playbackService?.onActivityStarted()
        }

        super.onStart()
        inappUpdater?.checkUpdate()

        if (shouldShowNotificationPermissionDialog()) {
            NotificationsPermissionDialogFragment().show(
                supportFragmentManager,
                "NotificationPermissionNavScreen"
            )
        }
    }

    override fun onResume() {
        super.onResume()
        billingHelper?.checkUnconsumedPurchases()
    }

    override fun onStop() {
        playbackService?.onActivityStopped()
        super.onStop()
    }

    private fun startAndBindService(serviceAction: (() -> Unit)? = null) {
        startService(playbackServiceIntent)
        lifecycleScope.launch {
            delay(500L)
            if (applicationContext.bindService(
                    playbackServiceIntent,
                    serviceConnection,
                    Context.BIND_IMPORTANT
                )
            ) {
                serviceAction?.invoke()
            }
        }
    }

    private fun unbindService() {
        if (isBound) {
            Napier.d("ACTIVITY unbindService", tag = PLAYBACK_SERVICE.name)
            applicationContext.unbindService(serviceConnection)
        }
    }

    override fun startService(intent: Intent): ComponentName? =
        try {
            super.startService(intent)
        } catch (e: IllegalArgumentException) {
            // The process is classed as idle by the platform.
            // Starting a background service is not allowed in this state.
            Napier.d("Failed to start service (process is idle).", tag = PLAYBACK_SERVICE.name)
            componentName
        } catch (e: IllegalStateException) {
            // The app is in background, starting service is disallow
            Napier.d("Failed to start service (app is in background)", tag = PLAYBACK_SERVICE.name)
            componentName
        }

    private fun root(componentContext: ComponentContext): RootComponent =
        RootComponentImpl(
            componentContext = componentContext,
            storeFactory = LoggingStoreFactory(DefaultStoreFactory()),
            deps = RootDeps(
                filer = AndroidFiler(this),
                analytics = app.analytics,
                playerBus = app.playerBus,
                platformApi = platformApi,
                dispatchers = dispatchers(),
                configReader = AndroidConfigReader(this),
                billingHelper = billingHelper,
                connectivityObserver = app.connectivityObserver,
                stringResourceHandler = AndroidStringResourceHandler(this),
            )
        )

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            val title = email_title.resolve(this@MainActivity)
            val body = email_body.resolve(this@MainActivity)

            val uriText =
                "mailto:aleksey.blekot@gmail.com?subject=$title&body=$body \uD83D\uDE07"
            data = Uri.parse(uriText)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Napier.e("No activity for $intent")
        }
    }

    private fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        val chooser = Intent.createChooser(intent, "Open Url")
        if (chooser.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        } else {
            Toast.makeText(this, "Can't open $link", Toast.LENGTH_SHORT).show()
            Napier.e("No activity for $intent")
        }
    }

    private fun rateUs() {
        val uri: Uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )

        if (goToMarket.resolveActivity(packageManager) != null) {
            startActivity(goToMarket)
        } else {
            Napier.e("No activity for $goToMarket")
        }
    }

    private fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=$packageName")
        }
        Intent.createChooser(intent, "Share via")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Napier.e("No activity for $intent")
        }
    }

    private fun selectTtsVoice() {
        val intent = Intent("com.android.settings.TTS_SETTINGS")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Napier.e("No activity for $intent")
        }
    }

    private fun inappReview() =
        showInappReview(this)

    private fun observeNotificationPermissionDialog() {
        createScreenResultProvider<NotificationsPermissionDialogFragment.ScreenResult>(
            resultKey = NotificationsPermissionDialogFragment.RESULT_KEY
        ).observe {
            if (it.allowed) {
                lifecycleScope.launch {
                    requestNotificationPermission()
                }
            }
        }
    }

    private fun addActivity() {
        PermissionManager.attachActivity(this)
    }

    private fun removeActivity() {
        PermissionManager.detachActivity(this)
    }
}
