import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask

plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("kotlin-parcelize")
}

kotlin {
//    val xcf = XCFramework()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries{
            framework {
                baseName = "Prabhupada"
//                xcf.add(this)

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

    //Build tasks for fat frameworks
//    val iosX64 = targets.getByName<KotlinNativeTarget>("iosX64")
//    val iosArm64 = targets.getByName<KotlinNativeTarget>("iosArm64")
//    val iosSimulatorArm64 = targets.getByName<KotlinNativeTarget>("iosSimulatorArm64")
//
//    val frameworkTargets = listOf(iosX64, iosArm64, iosSimulatorArm64).map { it.binaries.getFramework(DEBUG) }
//
//    tasks.register<FatFrameworkTask>("debugFatFramework") {
//        group = "ios"
//        baseName = "Prabhupada"
//        description = "Builds a universal (fat) debug framework"
//
//        from(frameworkTargets)
//    }
}

tasks.register<FatFrameworkTask>("debugFatFramework") {
    group = "ios"
    baseName = "Prabhupada"
    description = "Builds a universal (fat) debug framework"

    val targets = mapOf(
        "iosX64" to kotlin.targets.getByName<KotlinNativeTarget>("iosX64"),
//        "iosArm64" to kotlin.targets.getByName<KotlinNativeTarget>("iosArm64"),
        "iosSimulatorArm64" to kotlin.targets.getByName<KotlinNativeTarget>("iosSimulatorArm64")
    )

    from(
        targets.toList().map {
            it.second.binaries.getFramework(DEBUG)
        }
    )
}
