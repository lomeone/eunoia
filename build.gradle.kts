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

    tasks.test {
        useJUnitPlatform()
        finalizedBy(tasks.koverVerify, tasks.koverHtmlReport, tasks.koverXmlReport)
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

coveralls {
    jacocoReportPath = "${projectDir}/build/reports/kover/report.xml"
    sourceDirs = subprojects.map { it.sourceSets.main.get().allSource.srcDirs.toList() }
        .toList().flatten().map { relativePath(it) }
}

sonar {
    properties {
        property("sonar.projectKey", "lomeone_eunoia")
        property("sonar.organization", "lomeone")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "${projectDir}/build/reports/kover/report.xml")
    }
}
