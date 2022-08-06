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
    ":android-player",
    ":android-ui",
    ":android-user-app",
    ":androidApp",
    ":common:root",
    ":common:data",
    ":common:details-api",
    ":common:details-impl",
    ":common:list-api",
    ":common:list-impl",
    ":common:player-api",
    ":common:player-impl",
    ":common:resources",
    ":common:settings-api",
    ":common:settings-impl",
    ":common:utils",
)

buildCache {
    local {
//        directory = org.gradle.internal.component.external.model.ComponentVariant.File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 2
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
