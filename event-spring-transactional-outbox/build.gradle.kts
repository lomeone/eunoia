val eventSpringTransactionOutboxVersion: String by project
val springKafkaVersion: String by project
val kotlinxSerializationJsonVersion: String by project

version = eventSpringTransactionOutboxVersion

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
    kotlin("kapt")
    kotlin("plugin.jpa")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":event-core"))
    implementation(project(":event-spring-kafka"))

    // JSON
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationJsonVersion")

    // Kafka
    api("org.springframework.kafka:spring-kafka:$springKafkaVersion")

    // DB
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    kapt("jakarta.persistence:jakarta.persistence-api")
    kapt("jakarta.annotation:jakarta.annotation-api")
}
