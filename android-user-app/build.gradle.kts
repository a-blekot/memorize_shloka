import java.util.Properties
import java.io.FileInputStream

plugins {
    id("dev.icerock.mobile.multiplatform-resources")
    id("android-application-convention")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("kotlin-parcelize")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    signingConfigs {
        maybeCreate( "config").apply {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        maybeCreate("debug").apply {
            signingConfig = signingConfigs.getByName("config")
        }

        maybeCreate("release").apply {
            signingConfig = signingConfigs.getByName("config")
        }
    }
}

dependencies {
    implementation(projects.androidPlayer)
    implementation(projects.androidUi)
    implementation(projects.common.data)
    implementation(projects.common.donationsApi)
    implementation(projects.common.listApi)
    implementation(projects.common.listImpl)
    implementation(projects.common.playerApi)
    implementation(projects.common.playerImpl)
    implementation(projects.common.settingsApi)
    implementation(projects.common.resources)
    implementation(projects.common.root)
    implementation(projects.common.utils)

    implementation(libs.accompanist.insets)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.mvikotlin.bndl)
    implementation(libs.decompose.decompose)
    implementation(libs.decompose.extCompose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    debugImplementation(libs.napier.android.debug)
    releaseImplementation(libs.napier.android.release)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.crashlytics.ktx)

    implementation(libs.play.core)
    implementation(libs.billing)
    implementation(libs.material.xml)
}