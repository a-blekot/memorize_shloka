plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.bundles.plaginz)
}

val rootDirProject = file("../")

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("build-logic/src/main/kotlin")
}
