plugins {
    id("com.android.library")
    id("kotlin-base-convention")
    id("android-base-convention")
    id("dev.icerock.mobile.multiplatform-resources")
    kotlin("android")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.a_blekot.shlokas.android_ui"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.common.data)
    implementation(projects.common.detailsApi)
    implementation(projects.common.donationsApi)
    implementation(projects.common.listApi)
    implementation(projects.common.playerApi)
    implementation(projects.common.resources)
    implementation(projects.common.settingsApi)
    implementation(projects.common.utils)

    implementation(libs.bundles.androidx.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.decompose.decompose)
    implementation(libs.decompose.extCompose)
    implementation(libs.ui.lottie.compose)

    debugImplementation(libs.napier.android.debug)
    releaseImplementation(libs.napier.android.release)
}