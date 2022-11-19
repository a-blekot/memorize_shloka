plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("kotlin-parcelize")
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