plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("kotlin-parcelize")
}

android{
    namespace = "com.a_blekot.shlokas.common.details_impl"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common.data)
                implementation(projects.common.detailsApi)
                implementation(projects.common.utils)

                implementation(libs.decompose.decompose)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.mvikotlin.mvikotlin)
                implementation(libs.mvikotlin.extensions.coroutines)
                implementation(libs.napier)
            }
        }
    }
}
