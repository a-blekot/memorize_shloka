import ApkConfig.APPLICATION_ID
import ApkConfig.APPLICATION_ID_SUFFIX
import ApkConfig.VERSION_CODE
import ApkConfig.VERSION_NAME

plugins {
    kotlin("android")
    id("com.android.application")
    id("kotlin-base-convention")
    id("android-base-convention")
}

android {
    namespace = APPLICATION_ID

    defaultConfig {
        applicationId = APPLICATION_ID
        versionCode = VERSION_CODE
        versionName = VERSION_NAME
    }

    buildFeatures {
        compose = true
    }

    bundle {
        language {
            // Specifies that the app bundle should not support
            // configuration APKs for language resources. These
            // resources are instead packaged with each base and
            // dynamic feature APK.
            enableSplit = false
        }
    }

    buildTypes {
        debug {
            versionNameSuffix = " DEBUG"
            applicationIdSuffix = APPLICATION_ID_SUFFIX
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}
