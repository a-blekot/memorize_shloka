plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.listentoprabhupada.common.donations_impl"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common.data)
                implementation(projects.common.donationsApi)
                implementation(projects.common.resourcez)
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