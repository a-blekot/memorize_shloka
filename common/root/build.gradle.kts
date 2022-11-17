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
        it.binaries{
            framework {
                baseName = "Prabhupada"

                export(projects.common.data)
                export(projects.common.detailsApi)
                export(projects.common.donationsApi)
                export(projects.common.listApi)
                export(projects.common.playerApi)
                export(projects.common.playerImpl)
                export(projects.common.resources)
                export(projects.common.settingsApi)
                export(projects.common.utils)

                export(libs.decompose.decompose)
                export(libs.essenty.lifecycle)
                export(libs.moko.resources)
                export(libs.mvikotlin.main)
                export(libs.napier)
            }
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
                api(projects.common.data)
                api(projects.common.detailsApi)
                api(projects.common.donationsApi)
                api(projects.common.listApi)
                api(projects.common.playerApi)
                api(projects.common.playerImpl)
                api(projects.common.resources)
                api(projects.common.settingsApi)
                api(projects.common.utils)

                api(libs.decompose.decompose)
                api(libs.essenty.lifecycle)
                api(libs.moko.resources)
                api(libs.mvikotlin.main)
                api(libs.napier)
            }
        }
    }
}
