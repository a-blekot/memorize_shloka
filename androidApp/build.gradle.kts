plugins {
    id("android-application-convention")
    id("kotlin-parcelize")
}

dependencies {
    implementation(projects.common.data)
    implementation(projects.common.detailsApi)
    implementation(projects.common.detailsImpl)
    implementation(projects.common.listApi)
    implementation(projects.common.listImpl)
    implementation(projects.common.playerApi)
    implementation(projects.common.playerImpl)
    implementation(projects.common.root)
    implementation(projects.common.utils)

    implementation(libs.accompanist.insets)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.mvikotlin.bndl)
    implementation(libs.decompose.decompose)
    implementation(libs.decompose.extCompose)
    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.ui)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.napier.android)
}