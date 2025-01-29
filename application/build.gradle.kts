plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":primary-adapters"))
    implementation(project(":secondary-adapters"))
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.bundles.spring)
    implementation(libs.bundles.exposed.spring)
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