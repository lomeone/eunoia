group = rootProject.group
version = "1.0-SNAPSHOT"

plugins {
    id("com.gradleup.shadow") version "8.3.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Kafka Connect API
    implementation("org.apache.kafka:connect-api:3.4.1")

    // Debezium Core
    implementation("io.debezium:debezium-core:2.7.1.Final") // Debezium 버전에 맞춰 조정하세요

    // Debezium MySQL Connector
    implementation("io.debezium:debezium-connector-mysql:2.7.1.Final") // Debezium 버전에 맞춰 조정하세요

    // kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
