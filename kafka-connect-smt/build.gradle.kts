version = "0.0.1"

val kafkaConnectVersion: String by project
val kotlinSerializationVersion: String by project
val slf4jApiVersion: String by project

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

    // kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")

    // slf4j log
    implementation("org.slf4j:slf4j-api:$slf4jApiVersion")
}

tasks.test {
    useJUnitPlatform()
}
