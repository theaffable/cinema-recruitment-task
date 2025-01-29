plugins {
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.exposed.core)
    testImplementation(kotlin("test"))
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