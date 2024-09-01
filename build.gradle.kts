val groupName: String by project

plugins {
    kotlin("jvm")
}

allprojects {
    group = groupName
    version = "1.0-SNAPSHOT"

    apply {
        plugin("kotlin")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(kotlin("test"))
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
