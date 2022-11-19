plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("kotlin-parcelize")
}

android {
    namespace = "com.a_blekot.shlokas.common.list_api"
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
