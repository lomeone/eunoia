val projectName: String by settings
rootProject.name = projectName

pluginManagement {
    val kotlinVersion: String by settings
    val shadowVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("com.gradleup.shadow") version shadowVersion
    }
}


include("exception")
include("transaction-outbox")
include("event-core")
