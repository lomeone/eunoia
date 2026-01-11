val kotlinUtilVersion: String by project

val kotlinxSerializationVersion: String by project

version = kotlinUtilVersion

plugins {
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":exception"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:$kotlinxSerializationVersion")

    implementation(platform("aws.sdk.kotlin:bom:1.3.112"))
    implementation("aws.sdk.kotlin:kms")

    kapt("jakarta.persistence:jakarta.persistence-api")
}
