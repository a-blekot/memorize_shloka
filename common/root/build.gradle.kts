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
                implementation(projects.common.data)
                implementation(projects.common.detailsApi)
                implementation(projects.common.detailsImpl)
                implementation(projects.common.donationsApi)
                implementation(projects.common.donationsImpl)
                implementation(projects.common.listApi)
                implementation(projects.common.listImpl)
                implementation(projects.common.playerApi)
                implementation(projects.common.playerImpl)
                implementation(projects.common.settingsApi)
                implementation(projects.common.settingsImpl)
                implementation(projects.common.utils)
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
