version = "0.0.1"

val springKafkaVersion: String by project

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

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":event-core"))

    // Kafka
    api("org.springframework.kafka:spring-kafka:$springKafkaVersion")
}
