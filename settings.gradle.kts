val projectName: String by settings
rootProject.name = projectName

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val shadowVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("com.gradleup.shadow") version shadowVersion
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include("event-core")
include("event-spring-kafka")
include("event-spring-transactional-outbox")
include("exception")
include("kafka-connect-smt")
include("kotlin-util")
include("spring-web-rest")
include("spring-web-dgs")
