plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.a_blekot.shlokas.common.settings_api"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common.data)
                implementation(libs.decompose.decompose)
            }
        }
    }
}
