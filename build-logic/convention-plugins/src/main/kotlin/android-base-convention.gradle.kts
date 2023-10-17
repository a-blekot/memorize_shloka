import ApkConfig.COMPILE_SDK_VERSION
import ApkConfig.MIN_SDK_VERSION
import ApkConfig.TARGET_SDK_VERSION
import ApkConfig.JAVA_VERSION
import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

configure<BaseExtension> {
    compileSdkVersion(COMPILE_SDK_VERSION)

    defaultConfig {
        minSdk = MIN_SDK_VERSION
        targetSdk = TARGET_SDK_VERSION
    }

    buildTypes {
        maybeCreate("debug").apply {
            isDebuggable = true
            isMinifyEnabled = false
        }

        maybeCreate("release").apply {
            isDebuggable = false
            isMinifyEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = JAVA_VERSION
        targetCompatibility = JAVA_VERSION
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}
