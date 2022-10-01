plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("kotlin-parcelize")
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
