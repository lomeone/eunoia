val projectName: String by settings
rootProject.name = projectName

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        maven("https://packages.confluent.io/maven/")
    }
}

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val shadowVersion: String by settings

    plugins {
        kotlin("plugin.spring") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("com.gradleup.shadow") version shadowVersion
    }
}

include("event-core")
include("event-spring-kafka")
include("event-spring-transactional-outbox")
include("exception")
include("kafka-connect-smt")
include("kotlin-util")
include("security")
include("spring-web")
include("aws")
