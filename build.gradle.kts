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
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.spring)
    implementation(libs.okhttp.coroutines)
    implementation(libs.okhttp)
    implementation(libs.resilience4j)
    implementation(libs.bundles.exposed.spring)
    testImplementation(kotlin("test"))
    testImplementation(libs.bundles.integration.test)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll(
            // enable kotlin support for JSR 305 annotations (@NotNull etc.) - this makes dealing with Spring nullable types less painful
            "-Xjsr305=strict",
            // enable kotlin built-in Uuid type
            "-opt-in=kotlin.uuid.ExperimentalUuidApi"
        )
    }
}