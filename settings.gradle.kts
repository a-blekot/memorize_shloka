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
    ":common:data",
    ":common:details-api",
    ":common:details-impl",
    ":common:donations-api",
    ":common:donations-impl",
    ":common:list-api",
    ":common:list-impl",
    ":common:player-api",
    ":common:player-impl",
    ":common:resourcez",
    ":common:root",
    ":common:settings-api",
    ":common:settings-impl",
    ":common:utils",
    ":common:utils-coroutines",
)

buildCache {
    local {
//        directory = org.gradle.internal.component.external.model.ComponentVariant.File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 2
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
