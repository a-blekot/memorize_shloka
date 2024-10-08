plugins {
    id("dev.icerock.mobile.multiplatform-resources")
    id("android-application-convention")
    id("kotlin-parcelize")
}

dependencies {
    implementation(projects.androidPlayer)
    implementation(projects.androidUi)
    implementation(projects.common.data)
    implementation(projects.common.detailsApi)
    implementation(projects.common.detailsImpl)
    implementation(projects.common.listApi)
    implementation(projects.common.listImpl)
    implementation(projects.common.playerApi)
    implementation(projects.common.playerImpl)
    implementation(projects.common.settingsApi)
    implementation(projects.common.resourcez)
    implementation(projects.common.root)
    implementation(projects.common.utils)

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
}