plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.decompose.decompose)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.mvikotlin.extensions.coroutines)
                implementation(libs.mvikotlin.mvikotlin)
                implementation(libs.mvikotlin.rx)
                implementation(libs.napier)
            }
        }
    }
}
