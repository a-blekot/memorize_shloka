import org.gradle.api.JavaVersion

object ApkConfig {
    const val APPLICATION_ID = "com.a_blekot.memorize_shloka"
    const val APPLICATION_ID_SUFFIX = ""//".dev"

    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 34
    const val COMPILE_SDK_VERSION = 34

    const val VERSION_CODE = 22
    const val VERSION_NAME = "2023.10.17.01"

    val JAVA_VERSION = JavaVersion.VERSION_11
    val JAVA_VERSION_NAME = JAVA_VERSION.toString()
}