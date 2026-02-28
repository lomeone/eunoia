plugins {
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":exception"))

    implementation(libs.kotlinx.serialization)
}
