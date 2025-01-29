plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "cinema"
include("domain")
include("secondary-adapters")
include("primary-adapters")
