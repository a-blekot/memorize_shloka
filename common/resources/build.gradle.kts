plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("dev.icerock.mobile.multiplatform-resources")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(libs.moko.resources)
            }
        }
        androidMain {
            dependencies {
                api(libs.moko.resources.compose)
            }
        }

    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.a_blekot.shlokas.common.resources"
}
