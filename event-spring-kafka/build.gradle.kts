val eventSpringKafkaVersion: String by project

val springKafkaVersion: String by project

version = eventSpringKafkaVersion

plugins {
    kotlin("plugin.spring")
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

    // Kafka
    api("org.springframework.kafka:spring-kafka:$springKafkaVersion")
}
