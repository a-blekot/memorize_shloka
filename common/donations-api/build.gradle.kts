plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.listentoprabhupada.common.donations_api"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common.data)
                implementation(projects.common.utils)

                implementation(libs.decompose.decompose)
            }
        }
    }
}