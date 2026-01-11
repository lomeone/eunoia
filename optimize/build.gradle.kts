plugins {
    kotlin("jvm")
}

group = "com.lomeone.eunoia"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}