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

# Kotlin serialization looks up the generated serializer classes through a function on companion
# objects. The companions are looked up reflectively so we need to explicitly keep these functions.
-keepclasseswithmembers class **.*$Companion {
    kotlinx.serialization.KSerializer serializer(...);
}
# If a companion has the serializer function, keep the companion field on the original type so that
# the reflective lookup succeeds.
-if class **.*$Companion {
  kotlinx.serialization.KSerializer serializer(...);
}
-keepclassmembers class <1>.<2> {
  <1>.<2>$Companion Companion;
}

#Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception


# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.a_blekot.shlokas.android_player.PendingIntentProvider
-dontwarn com.a_blekot.shlokas.android_player.PlaybackService$PlaybackBinder
-dontwarn com.a_blekot.shlokas.android_player.PlaybackService
-dontwarn com.a_blekot.shlokas.android_ui.ResourcesAndroidKt
-dontwarn com.a_blekot.shlokas.android_ui.SpacerKt
-dontwarn com.a_blekot.shlokas.android_ui.custom.BottomSheetCardKt
-dontwarn com.a_blekot.shlokas.android_ui.custom.ButtonKt
-dontwarn com.a_blekot.shlokas.android_ui.theme.ThemeKt
-dontwarn com.a_blekot.shlokas.android_ui.view.donations.DonationsViewKt
-dontwarn com.a_blekot.shlokas.android_ui.view.list.ListViewKt
-dontwarn com.a_blekot.shlokas.android_ui.view.player.PlayerViewKt
-dontwarn com.a_blekot.shlokas.android_ui.view.settings.SettingsViewKt
-dontwarn com.a_blekot.shlokas.common.data.Donation
-dontwarn com.a_blekot.shlokas.common.data.DonationLevel
-dontwarn com.a_blekot.shlokas.common.data.DonationLevelKt
-dontwarn com.a_blekot.shlokas.common.data.PlatformApi
-dontwarn com.a_blekot.shlokas.common.list_api.ListComponent
-dontwarn com.a_blekot.shlokas.common.player_api.PlayerBus
-dontwarn com.a_blekot.shlokas.common.player_api.PlayerComponent
-dontwarn com.a_blekot.shlokas.common.player_impl.PlayerBusImpl
-dontwarn com.a_blekot.shlokas.common.resources.MR$strings
-dontwarn com.a_blekot.shlokas.common.resources.StringsKt
-dontwarn com.a_blekot.shlokas.common.settings_api.SettingsComponent
-dontwarn com.a_blekot.shlokas.common.utils.AndroidFiler
-dontwarn com.a_blekot.shlokas.common.utils.Filer
-dontwarn com.a_blekot.shlokas.common.utils.LocaleKt
-dontwarn com.a_blekot.shlokas.common.utils.NapierProxyKt
-dontwarn com.a_blekot.shlokas.common.utils.SettingsKt
-dontwarn com.a_blekot.shlokas.common.utils.analytics.Analytics
-dontwarn com.a_blekot.shlokas.common.utils.billing.BillingEvent$Error
-dontwarn com.a_blekot.shlokas.common.utils.billing.BillingEvent$PurchaseSuccess
-dontwarn com.a_blekot.shlokas.common.utils.billing.BillingEvent
-dontwarn com.a_blekot.shlokas.common.utils.billing.BillingHelper
-dontwarn com.a_blekot.shlokas.common.utils.billing.BillingHelperDefault
-dontwarn com.a_blekot.shlokas.common.utils.billing.BillingOperation
-dontwarn com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserver
-dontwarn com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserverAndroid
-dontwarn com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider
-dontwarn com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProviderImplKt
-dontwarn com.a_blekot.shlokas.common.utils.resources.AndroidConfigReader
-dontwarn com.a_blekot.shlokas.common.utils.resources.AndroidStringResourceHandler
-dontwarn com.a_blekot.shlokas.common.utils.resources.ConfigReader
-dontwarn com.a_blekot.shlokas.common.utils.resources.StringResourceHandler
-dontwarn com.ablekot.shlokas.common.root.RootComponent$Child$Donations
-dontwarn com.ablekot.shlokas.common.root.RootComponent$Child$List
-dontwarn com.ablekot.shlokas.common.root.RootComponent$Child$Player
-dontwarn com.ablekot.shlokas.common.root.RootComponent$Child$Settings
-dontwarn com.ablekot.shlokas.common.root.RootComponent$Child
-dontwarn com.ablekot.shlokas.common.root.RootComponent
-dontwarn com.ablekot.shlokas.common.root.RootComponentImpl
-dontwarn com.ablekot.shlokas.common.root.RootDeps
-dontwarn com.listentoprabhupada.common.donations_api.DonationsComponent
