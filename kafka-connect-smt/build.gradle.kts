val kafkaConnectSmtVersion: String by project

val kafkaConnectVersion: String by project
val kotlinSerializationVersion: String by project
val slf4jApiVersion: String by project

version = kafkaConnectSmtVersion

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
