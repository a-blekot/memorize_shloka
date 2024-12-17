plugins {
    kotlin("multiplatform")
    id("kotlin-base-convention")
}

kotlin {
    androidTarget()

//    iosX64()
    iosArm64()
    iosSimulatorArm64()

//    applyDefaultHierarchyTemplate()
}
