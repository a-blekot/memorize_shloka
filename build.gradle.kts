buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.bundles.plaginz)
    }
}

plugins {
    id("org.barfuin.gradle.taskinfo") version "1.0.5"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}