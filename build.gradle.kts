val groupName: String by project

val kotestVersion: String by project

plugins {
    kotlin("jvm")
    `maven-publish`
    id("org.jetbrains.kotlin.plugin.serialization")
}

allprojects {
    group = groupName

    apply {
        plugin("kotlin")
    }

    repositories {
        mavenCentral()
    }

    dependencies {

        // kotest
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-property:$kotestVersion")
    }
}

subprojects {
    apply {
        plugin("maven-publish")
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/lomeone/eunoia")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
        publications {
            register<MavenPublication>("gpr") {
                from(components["java"])
            }
        }
    }

    tasks.test {
        useJUnitPlatform()
    }
}
