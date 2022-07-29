pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "shlokas"
include(
    ":androidApp",
    ":common:root",
    ":common:data",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")