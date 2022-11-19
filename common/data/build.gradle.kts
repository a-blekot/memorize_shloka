plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("kotlin-parcelize")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.a_blekot.shlokas.common.data"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.decompose.decompose)
                implementation(libs.kotlinx.serialization.json)
            }
        }
    }
}
