package com.a_blekot.memorize_shloka

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.a_blekot.memorize_shloka.utils.AnalyticsAndroid
import com.a_blekot.memorize_shloka.utils.AnalyticsAndroidDebug
import com.a_blekot.memorize_shloka.utils.CrashlyticsAntilog
import com.a_blekot.shlokas.common.player_api.PlayerBus
import com.a_blekot.shlokas.common.player_impl.PlayerBusImpl
import com.a_blekot.shlokas.common.utils.analytics.Analytics
import com.a_blekot.shlokas.common.utils.LogTag.LIFECYCLE_ACTIVITY
import com.a_blekot.shlokas.common.utils.LogTag.LIFECYCLE_APP
import com.a_blekot.shlokas.common.utils.checkLocale
import com.a_blekot.shlokas.common.utils.dispatchers.dispatchers
import com.a_blekot.shlokas.common.utils.onAppLaunch
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainApp : Application() {
    companion object {
        lateinit var app: MainApp
    }

    @Volatile
    var currentActivity: Activity? = null
        private set

    lateinit var playerBus: PlayerBus
    lateinit var analytics: Analytics

    private val lifecycleEventObserver = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> Napier.d( "APPLICATION ON_CREATE", tag = LIFECYCLE_APP.name)
            Lifecycle.Event.ON_START -> Napier.d( "APPLICATION ON_START", tag = LIFECYCLE_APP.name)
            Lifecycle.Event.ON_RESUME -> Napier.d( "APPLICATION ON_RESUME", tag = LIFECYCLE_APP.name)
            Lifecycle.Event.ON_PAUSE -> Napier.d( "APPLICATION ON_PAUSE", tag = LIFECYCLE_APP.name)
            Lifecycle.Event.ON_STOP -> Napier.d( "APPLICATION ON_STOP", tag = LIFECYCLE_APP.name)
            Lifecycle.Event.ON_DESTROY -> Napier.d( "APPLICATION ON_DESTROY", tag = LIFECYCLE_APP.name)
            else -> {
                /** do nothing **/
                /** do nothing **/
            }
        }
    }

    private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {
            Napier.d( "onActivityPaused", tag = LIFECYCLE_ACTIVITY.name)
        }

        override fun onActivityStarted(activity: Activity) {
            Napier.d( "onActivityStarted", tag = LIFECYCLE_ACTIVITY.name)
        }

        override fun onActivityDestroyed(activity: Activity) {
            Napier.d( "onActivityDestroyed", tag = LIFECYCLE_ACTIVITY.name)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            Napier.d( "onActivitySaveInstanceState", tag = LIFECYCLE_ACTIVITY.name)
        }

        override fun onActivityStopped(activity: Activity) {
            Napier.d( "onActivityStopped", tag = LIFECYCLE_ACTIVITY.name)
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Napier.d( "onActivityCreated", tag = LIFECYCLE_ACTIVITY.name)
        }

        override fun onActivityResumed(activity: Activity) {
            Napier.d( "onActivityResumed", tag = LIFECYCLE_ACTIVITY.name)
            currentActivity = activity
        }
    }

    override fun onCreate() {
        super.onCreate()

        checkLocale(Locale.current.language)

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)

        if (BuildConfig.DEBUG) {
            analytics = AnalyticsAndroidDebug()
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
            Napier.base(DebugAntilog())
        } else {
            analytics = AnalyticsAndroid(this)
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
            Napier.base(CrashlyticsAntilog(this))
        }

        app = this
        playerBus = PlayerBusImpl(dispatchers())
        Napier.d("app.playerBus = $playerBus", tag="PlayerBus")
        onAppLaunch()
    }
}