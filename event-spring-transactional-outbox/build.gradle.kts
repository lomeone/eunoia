group = "${rootProject.group}.event.spring.transactional.outbox"
version = "1.0-SNAPSHOT"

val springKafkaVersion: String by project

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

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":event-core"))

    // Kafka
    api("org.springframework.kafka:spring-kafka:$springKafkaVersion")

    // DB
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    kapt("jakarta.persistence:jakarta.persistence-api")
    kapt("jakarta.annotation:jakarta.annotation-api")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
