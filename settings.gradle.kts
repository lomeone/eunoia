val projectName: String by settings
rootProject.name = projectName

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val shadowVersion: String by settings
    val koverVersion: String by settings
    val coverallsVersion: String by settings
    val sonarqubeVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("com.gradleup.shadow") version shadowVersion
        id("org.jetbrains.kotlinx.kover") version koverVersion
        id("com.github.kt3k.coveralls") version coverallsVersion
        id("org.sonarqube") version sonarqubeVersion
    }
}

include("event-core")
include("event-spring-kafka")
include("event-spring-transactional-outbox")
include("exception")
include("kafka-connect-smt")
include("kotlin-util")
include("spring-web-rest")
include("spring-web-dgs")
