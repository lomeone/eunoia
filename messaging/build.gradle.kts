plugins {
    kotlin("jvm")
}

version = "0.0.1"

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
