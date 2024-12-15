# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
# -keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5
-printmapping mapping.txt

# Keep data models (for json parsing)
#-keep class com.a_blekot.shlokas.common.data.** { *; }

# Keep Parcelable model
#-keep class * implements android.os.Parcelable { *; }

# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

# Don't print notes about potential mistakes or omissions in the configuration for kotlinx-serialization classes
# See also https://github.com/Kotlin/kotlinx.serialization/issues/1900
-dontnote kotlinx.serialization.**

# Serialization core uses `java.lang.ClassValue` for caching inside these specified classes.
# If there is no `java.lang.ClassValue` (for example, in Android), then R8/ProGuard will print a warning.
# However, since in this case they will not be used, we can disable these warnings
-dontwarn kotlinx.serialization.internal.ClassValueReferences

#Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
#-keep class com.a_blekot.shlokas.android_player.PendingIntentProvider
#-keep class com.a_blekot.shlokas.android_player.PlaybackService$PlaybackBinder
#-keep class com.a_blekot.shlokas.android_player.PlaybackService
#-keep class com.a_blekot.shlokas.android_ui.ResourcesAndroidKt
#-keep class com.a_blekot.shlokas.android_ui.SpacerKt
#-keep class com.a_blekot.shlokas.android_ui.custom.BottomSheetCardKt
#-keep class com.a_blekot.shlokas.android_ui.custom.ButtonKt
#-keep class com.a_blekot.shlokas.android_ui.theme.ThemeKt
#-keep class com.a_blekot.shlokas.android_ui.view.donations.DonationsViewKt
#-keep class com.a_blekot.shlokas.android_ui.view.list.ListViewKt
#-keep class com.a_blekot.shlokas.android_ui.view.player.PlayerViewKt
#-keep class com.a_blekot.shlokas.android_ui.view.settings.SettingsViewKt
#-keep class com.a_blekot.shlokas.common.data.Donation
#-keep class com.a_blekot.shlokas.common.data.DonationLevel
#-keep class com.a_blekot.shlokas.common.data.DonationLevelKt
#-keep class com.a_blekot.shlokas.common.data.PlatformApi
#-keep class com.a_blekot.shlokas.common.list_api.ListComponent
#-keep class com.a_blekot.shlokas.common.player_api.PlayerBus
#-keep class com.a_blekot.shlokas.common.player_api.PlayerComponent
#-keep class com.a_blekot.shlokas.common.player_impl.PlayerBusImpl
#-keep class com.a_blekot.shlokas.common.resources.MR$strings
#-keep class com.a_blekot.shlokas.common.resources.StringsKt
#-keep class com.a_blekot.shlokas.common.settings_api.SettingsComponent
#-keep class com.a_blekot.shlokas.common.utils.AndroidFiler
#-keep class com.a_blekot.shlokas.common.utils.Filer
#-keep class com.a_blekot.shlokas.common.utils.LocaleKt
#-keep class com.a_blekot.shlokas.common.utils.NapierProxyKt
#-keep class com.a_blekot.shlokas.common.utils.SettingsKt
#-keep class com.a_blekot.shlokas.common.utils.analytics.Analytics
#-keep class com.a_blekot.shlokas.common.utils.billing.BillingEvent$Error
#-keep class com.a_blekot.shlokas.common.utils.billing.BillingEvent$PurchaseSuccess
#-keep class com.a_blekot.shlokas.common.utils.billing.BillingEvent
#-keep class com.a_blekot.shlokas.common.utils.billing.BillingHelper
#-keep class com.a_blekot.shlokas.common.utils.billing.BillingHelperDefault
#-keep class com.a_blekot.shlokas.common.utils.billing.BillingOperation
#-keep class com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserver
#-keep class com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserverAndroid
#-keep class com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider
#-keep class com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProviderImplKt
#-keep class com.a_blekot.shlokas.common.utils.resources.AndroidConfigReader
#-keep class com.a_blekot.shlokas.common.utils.resources.AndroidStringResourceHandler
#-keep class com.a_blekot.shlokas.common.utils.resources.ConfigReader
#-keep class com.a_blekot.shlokas.common.utils.resources.StringResourceHandler
#-keep class com.ablekot.shlokas.common.root.RootComponent$Child$Donations
#-keep class com.ablekot.shlokas.common.root.RootComponent$Child$List
#-keep class com.ablekot.shlokas.common.root.RootComponent$Child$Player
#-keep class com.ablekot.shlokas.common.root.RootComponent$Child$Settings
#-keep class com.ablekot.shlokas.common.root.RootComponent$Child
#-keep class com.ablekot.shlokas.common.root.RootComponent
#-keep class com.ablekot.shlokas.common.root.RootComponentImpl
#-keep class com.ablekot.shlokas.common.root.RootDeps
#-keep class com.listentoprabhupada.common.donations_api.DonationsComponent

#-keep class com.a_blekot.shlokas.common.data.ShlokaId
#-keep class com.a_blekot.shlokas.common.data.tasks.IdleTask
#-keep class com.a_blekot.shlokas.common.data.tasks.NoAudioTask
#-keep class com.a_blekot.shlokas.common.data.tasks.PauseTask
#-keep class com.a_blekot.shlokas.common.data.tasks.PlayTask
#-keep class com.a_blekot.shlokas.common.data.tasks.PlayTranslationTask
#-keep class com.a_blekot.shlokas.common.data.tasks.ResetCounterTask
#-keep class com.a_blekot.shlokas.common.data.tasks.SetTrackTask
#-keep class com.a_blekot.shlokas.common.data.tasks.StopTask
#-keep class com.a_blekot.shlokas.common.data.tasks.Task
#-keep class com.a_blekot.shlokas.common.player_api.PlayerBus
#-keep class com.a_blekot.shlokas.common.player_api.PlayerFeedback$Ready
#-keep class com.a_blekot.shlokas.common.player_api.PlayerFeedback$Started
#-keep class com.a_blekot.shlokas.common.player_api.PlayerFeedback
#-keep class com.a_blekot.shlokas.common.utils.CoroutinesExtKt
#-keep class com.a_blekot.shlokas.common.utils.Sanskrit_utilsKt
#-keep class com.a_blekot.shlokas.common.utils.SettingsKt
#-keep class com.a_blekot.shlokas.common.utils.resources.ConfigReaderKt
