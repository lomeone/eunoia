group = "${rootProject.group}.kafka.connect.smt"
version = "1.0-SNAPSHOT"

val kafkaConnectVersion: String by project
val debeziumVersion: String by project
val kotlinSerializationVersion: String by project

plugins {
    id("com.gradleup.shadow")
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
    implementation("org.apache.kafka:connect-api:$kafkaConnectVersion")

    // Debezium Core
    implementation("io.debezium:debezium-core:$debeziumVersion")

    // kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
}

tasks.test {
    useJUnitPlatform()
}