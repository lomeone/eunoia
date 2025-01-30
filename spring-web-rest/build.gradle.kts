version = "0.0.1"

val springWebVersion: String by project

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
    implementation(project(":exception"))

    implementation("org.springframework:spring-web:$springWebVersion")
}
