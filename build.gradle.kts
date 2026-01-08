val groupName: String by project

val kotestVersion: String by project

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.coveralls)
    alias(libs.plugins.soraqube)
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

    dependencies {
        // kotest
        testImplementation(platform(catalog.kotest.bom))
        testImplementation(catalog.bundles.kotest.test.suite)
    }
}

dependencies {
    kover(project(":event-core"))
    kover(project(":event-spring-kafka"))
    kover(project(":event-spring-transactional-outbox"))
    kover(project(":exception"))
    kover(project(":kotlin-util"))
    kover(project(":security"))
    kover(project(":spring-web-dgs"))
    kover(project(":spring-web-rest"))
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
