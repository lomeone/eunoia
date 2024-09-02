plugins {
    kotlin("jvm")
}

group = "com.eunoia"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}