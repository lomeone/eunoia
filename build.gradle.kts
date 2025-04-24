val groupName: String by project

val kotestVersion: String by project

plugins {
    kotlin("jvm")
    `maven-publish`
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlinx.kover")
    id("com.github.kt3k.coveralls")
    id("org.sonarqube")
}

allprojects {
    group = groupName

    apply {
        plugin("kotlin")
        plugin("org.jetbrains.kotlinx.kover")
        plugin("org.sonarqube")
    }

    repositories {
        mavenCentral()
    }

    dependencies {

        // kotest
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-property:$kotestVersion")
    }

    kover {
        reports {
            total {
                verify {
                    rule {
                        minBound(0)
                    }
                }
            }
        }
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

dependencies {
    kover(project(":event-core"))
    kover(project(":event-spring-kafka"))
    kover(project(":event-spring-transactional-outbox"))
    kover(project(":exception"))
    kover(project(":kotlin-util"))
    kover(project(":spring-web-dgs"))
    kover(project(":spring-web-rest"))
}
