import java.util.Properties
import java.io.FileInputStream

plugins {
    id("dev.icerock.mobile.multiplatform-resources")
    id("android-application-convention")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("kotlin-parcelize")
    alias(libs.plugins.compose.compilerPlgn)
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
    implementation(projects.common.resourcez)
    implementation(projects.common.root)
    implementation(projects.common.utils)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.mvikotlin.bndl)
    implementation(libs.decompose.decompose)
    implementation(libs.decompose.extCompose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.material.xml)
    debugImplementation(libs.napier.android.debug)
    releaseImplementation(libs.napier.android.release)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.crashlytics.ktx)

    implementation(libs.play.inapp.review)
    implementation(libs.play.inapp.update)
    implementation(libs.billing)
}