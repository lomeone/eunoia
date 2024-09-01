plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
    kotlin("kapt")
    kotlin("plugin.jpa")
}

group = "${rootProject.group}.event.spring.transactional.outbox"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // DB
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    kapt("jakarta.persistence:jakarta.persistence-api")
    kapt("jakarta.annotation:jakarta.annotation-api")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
