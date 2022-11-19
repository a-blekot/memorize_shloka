plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
}

android {
    namespace = "com.a_blekot.shlokas.common.utils_coroutines"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
