val groupName: String by project

val kotestVersion: String by project

plugins {
    kotlin("jvm")
    `maven-publish`
    id("org.jetbrains.kotlin.plugin.serialization")
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
                url = uri("https://maven.pkg.github.com/lomeone/eunoia") // yourusername 및 your-repo를 실제 GitHub 사용자명 및 저장소명으로 변경
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
