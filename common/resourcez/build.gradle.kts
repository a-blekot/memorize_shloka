plugins {
    id("android-library-convention")
    id("kotlin-multiplatform-convention")
    id("dev.icerock.mobile.multiplatform-resources")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.a_blekot.shlokas.common.resources"

    sourceSets.getByName("main") {
//        res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
//        assets.srcDir(File(buildDir, "generated/moko/androidMain/assets"))
        java.srcDirs("build/generated/moko/androidMain/src")
        java.srcDirs("build/generated/kotlin/generateMRandroidMain")
        res.srcDirs("build/generated/moko-resources/androidMain/res")
    }
}

dependencies {
    commonMainApi(libs.moko.resources)
    androidMainApi(libs.moko.resources.compose)
}

multiplatformResources {
    iosBaseLocalizationRegion.set("ru")
    resourcesPackage.set("com.a_blekot.shlokas.common.resources")
//    configureCopyXCFrameworkResources("Prabhupada")
}
