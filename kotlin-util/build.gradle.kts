plugins {
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":exception"))

    implementation(platform(libs.kotlinx.serialization.bom))
    implementation(libs.kotlinx.serialization.json)
}
