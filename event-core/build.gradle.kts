version = "0.0.1"

val cloudeventsVersion: String by project

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
    // cloud event
    api("io.cloudevents:cloudevents-kafka:$cloudeventsVersion")
}

tasks.test {
    useJUnitPlatform()
}
