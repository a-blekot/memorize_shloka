buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.bundles.plaginz)
        classpath(libs.moko.resources.generator)
        classpath(libs.firebase.crashlytics.gradlePlgn)
        classpath(libs.google.services.gradlePlgn)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}