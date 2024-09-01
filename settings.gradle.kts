val projectName: String by settings
rootProject.name = projectName

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagement: String by settings
    val shadowVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagement
        id("com.gradleup.shadow") version shadowVersion
    }
}


include("event-core")
include("event-spring-transactional-outbox")
include("exception")
include("transactional-outbox")
