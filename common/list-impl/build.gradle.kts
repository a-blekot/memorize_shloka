plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.a_blekot.shlokas.common.list_impl"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common.data)
                implementation(projects.common.listApi)
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
