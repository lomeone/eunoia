import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val groupName: String by project

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.coveralls)
    alias(libs.plugins.soraqube)
    `java-library`
    `maven-publish`
}

val catalog = libs

allprojects {
    group = groupName

    apply {
        plugin("kotlin")
        plugin("org.jetbrains.kotlinx.kover")
        plugin("com.github.nbaztec.coveralls-jacoco")
        plugin("org.sonarqube")
    }
}

subprojects {
    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
        jvmToolchain(21)
    }

    apply {
        plugin("java-library")
        plugin("maven-publish")
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

    dependencies {
        // logging
        implementation(catalog.kotlin.logging)

        // kotest
        testImplementation(platform(catalog.kotest.bom))
        testImplementation(catalog.bundles.kotest.test.suite)
    }

    tasks.test {
        useJUnitPlatform()
        finalizedBy(tasks.koverVerify, tasks.koverHtmlReport, tasks.koverXmlReport)
    }
}

dependencies {
    kover(project(":event-core"))
    kover(project(":exception"))
    kover(project(":kotlin-util"))
    kover(project(":security"))
    kover(project(":spring-event-kafka"))
    kover(project(":spring-web"))
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

coverallsJacoco {
    reportPath = "${projectDir}/build/reports/kover/report.xml"
}

sonar {
    properties {
        property("sonar.projectKey", "lomeone_eunoia")
        property("sonar.organization", "lomeone")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "${projectDir}/build/reports/kover/report.xml")
    }
}
