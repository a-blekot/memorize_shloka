import org.gradle.api.JavaVersion

object ApkConfig {
    const val APPLICATION_ID = "com.a_blekot.memorize_shloka"
    const val APPLICATION_ID_SUFFIX = ""//".dev"

    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 33
    const val COMPILE_SDK_VERSION = 33

    const val VERSION_CODE = 19
    const val VERSION_NAME = "1.9.1"

    val JAVA_VERSION = JavaVersion.VERSION_1_8
    val JAVA_VERSION_NAME = JavaVersion.VERSION_1_8.toString()
}