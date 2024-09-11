plugins {
    id("com.gradleup.shadow")
}

group = "${rootProject.group}.kafka.connect.smt"
version = "1.0-SNAPSHOT"

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
    implementation("io.debezium:debezium-core:2.7.1.Final")

    // kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
}

tasks.test {
    useJUnitPlatform()
}