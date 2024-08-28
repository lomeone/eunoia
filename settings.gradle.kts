val projectName: String by settings
rootProject.name = projectName

pluginManagement {
    val kotlinVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
    }
}


include("transaction-outbox")
