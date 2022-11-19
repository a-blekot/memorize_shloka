plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("kotlin-parcelize")
}

android {
    namespace = "com.a_blekot.shlokas.common.player_api"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common.data)
                implementation(libs.decompose.decompose)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
