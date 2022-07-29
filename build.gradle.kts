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

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}