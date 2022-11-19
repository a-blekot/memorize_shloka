plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("dev.icerock.mobile.multiplatform-resources")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.a_blekot.shlokas.common.resources"

    sourceSets.getByName("main") {
        res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
        assets.srcDir(File(buildDir, "generated/moko/androidMain/assets"))
    }
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
    iosBaseLocalizationRegion = "ru"
    multiplatformResourcesPackage = "com.a_blekot.shlokas.common.resources"
}
