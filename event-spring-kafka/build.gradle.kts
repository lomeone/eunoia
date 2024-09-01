group = "${rootProject.group}.event.spring.kafka"
version = "1.0-SNAPSHOT"

val springKafkaVersion: String by project

plugins {}

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

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
