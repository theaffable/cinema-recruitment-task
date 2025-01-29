plugins {
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(libs.bundles.kotest)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    kotlin {
        jvmToolchain(21)
        compilerOptions {
            freeCompilerArgs.addAll(
                // enable kotlin built-in Uuid type
                "-opt-in=kotlin.uuid.ExperimentalUuidApi"
            )
        }
    }
}