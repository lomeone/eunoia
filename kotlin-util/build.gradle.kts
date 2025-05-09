val kotlinUtilVersion: String by project

val kotlinxSerializationVersion: String by project

version = kotlinUtilVersion

plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
}

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

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:$kotlinxSerializationVersion")

    implementation(platform("aws.sdk.kotlin:bom:1.3.112"))
    implementation("aws.sdk.kotlin:kms")
}
