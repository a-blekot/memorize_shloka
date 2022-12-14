plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("kotlin-parcelize")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.a_blekot.shlokas.common.utils"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common.data)
                implementation(projects.common.resources)

                implementation(libs.decompose.decompose)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.mvikotlin.extensions.coroutines)
                implementation(libs.mvikotlin.mvikotlin)
                implementation(libs.mvikotlin.rx)
                implementation(libs.napier)
                implementation(libs.settings)
            }
        }
    }
}
