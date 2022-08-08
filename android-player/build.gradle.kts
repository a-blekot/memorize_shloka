plugins {
    id("com.android.library")
    id("kotlin-base-convention")
    id("android-base-convention")
    kotlin("android")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(projects.common.data)
    implementation(projects.common.utils)
    implementation(projects.common.playerApi)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.ui)

//    implementation(libs.androidx.lifecycle.process)
//    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    debugImplementation(libs.napier.android.debug)
    releaseImplementation(libs.napier.android.release)
}