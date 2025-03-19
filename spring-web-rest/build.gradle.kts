val springWebRestVersion: String by project

val springFrameworkVersion: String by project

version = springWebRestVersion

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

dependencies {
    implementation(project(":exception"))

    implementation("org.springframework:spring-web:$springFrameworkVersion")
    implementation("org.springframework:spring-context:$springFrameworkVersion")
}
