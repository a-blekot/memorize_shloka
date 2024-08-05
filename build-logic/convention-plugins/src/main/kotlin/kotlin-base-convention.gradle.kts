import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0

tasks.withType<KotlinCompile> {
    compilerOptions {
        apiVersion.set(KOTLIN_2_0)
        jvmTarget = JvmTarget.JVM_11
    }
}
