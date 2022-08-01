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
    ":common:details-api",
    ":common:details-impl",
    ":common:list-api",
    ":common:list-impl",
    ":common:player-api",
    ":common:player-impl",
    ":common:utils",
)

buildCache {
    local {
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 2
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")