plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.bundles.plaginz)

// workaround to make libs accessible from convention-plugins
// https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

val rootDirProject = file("../")

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("build-logic/src/main/kotlin")
}
