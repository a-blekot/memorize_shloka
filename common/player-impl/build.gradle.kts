plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("kotlin-parcelize")
}

android {
    namespace = "com.a_blekot.shlokas.common.player_impl"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common.data)
                implementation(projects.common.playerApi)
                implementation(projects.common.resourcez)
                implementation(projects.common.utils)

//                implementation("com.soywiz.korlibs.korau:korau:3.0.0")
                implementation(libs.decompose.decompose)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.mvikotlin.mvikotlin)
                implementation(libs.mvikotlin.extensions.coroutines)
                implementation(libs.napier)
            }
        }
    }
}
