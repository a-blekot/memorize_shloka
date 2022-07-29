plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("kotlin-parcelize")
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "common"
//            export(projects.common.database)
        }
    }

    sourceSets {
        commonMain {
            dependencies {
//                implementation(projects.common.utils)
                implementation(projects.common.data)
                implementation(libs.mvikotlin.mvikotlin)
                implementation(libs.decompose.decompose)
            }
        }

        iosMain {
            dependencies {
//                api(projects.common.utils)
                api(projects.common.data)
                api(libs.decompose.decompose)
                api(libs.mvikotlin.main)
            }
        }
    }
}
