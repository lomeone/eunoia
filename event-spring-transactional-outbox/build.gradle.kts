val eventSpringTransactionOutboxVersion: String by project
val springKafkaVersion: String by project
val kotlinxSerializationJsonVersion: String by project

version = eventSpringTransactionOutboxVersion

plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(":event-core"))
    implementation(project(":spring-event-kafka"))

    // JSON
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationJsonVersion")

    // Kafka
    api("org.springframework.kafka:spring-kafka:$springKafkaVersion")

    // DB
    api(platform(libs.spring.boot.bom))
    api(libs.spring.boot.starter.data.jpa)
    kapt(libs.jakarta.persistence.api)
    kapt(libs.jakarta.annotation.api)
}
