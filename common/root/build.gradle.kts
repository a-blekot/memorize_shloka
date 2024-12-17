plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.ablekot.shlokas.common.root"
}

kotlin {
    listOf(
//        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries{
            framework {
                baseName = "Prabhupada"
                isStatic = true

                export(projects.common.data)
                export(projects.common.detailsApi)
                export(projects.common.donationsApi)
                export(projects.common.listApi)
                export(projects.common.playerApi)
                export(projects.common.playerImpl)
                export(projects.common.resourcez)
                export(projects.common.settingsApi)
                export(projects.common.utils)

                export(libs.decompose.decompose)
                export(libs.essenty.lifecycle)
                export(libs.essenty.state.keeper)
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
                api(projects.common.resourcez)
                api(projects.common.settingsApi)
                api(projects.common.utils)

                api(libs.decompose.decompose)
                api(libs.essenty.lifecycle)
                api(libs.essenty.state.keeper)
                api(libs.moko.resources)
                api(libs.mvikotlin.main)
                api(libs.napier)
            }
        }
    }
}
