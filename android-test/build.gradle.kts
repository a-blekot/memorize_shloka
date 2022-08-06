plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.a_blekot.shlokas.android_test"


    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.2")


}