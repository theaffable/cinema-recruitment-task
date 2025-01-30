plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.bundles.spring)
    implementation(libs.bundles.kotlin)
    testImplementation(kotlin("test"))
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.spring.webflux)
    testImplementation(libs.bundles.integration.test)
    testImplementation(libs.kotest.extensions.spring) {
        exclude(libs.mockito.core.get().toString())
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll(
            // enable kotlin built-in Uuid type
            "-opt-in=kotlin.uuid.ExperimentalUuidApi"
        )
    }
}