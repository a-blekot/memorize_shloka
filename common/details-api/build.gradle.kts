plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
}

android {
    namespace = "com.a_blekot.shlokas.common.details_api"
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
