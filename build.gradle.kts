val groupName: String by project

plugins {
    kotlin("jvm")
    `maven-publish`
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

subprojects {
    apply {
        plugin("maven-publish")
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    publishing {
//        publications {
//            create<MavenPublication>("mavenJava") {
//                from(components["java"])
//
//                // POM 메타데이터 설정
//                pom {
//                    name.set("eunoia")
//                    description.set("라이브러리 설명")
//                    url.set("https://github.com/comstering/eunoia")
//
//                    licenses {
//                        license {
//                            name.set("MIT License")
//                            url.set("https://opensource.org/licenses/MIT")
//                            distribution.set("repo")
//                        }
//                    }
//
//                    developers {
//                        developer {
//                            id.set("comstering")
//                            name.set("comstering")
//                            email.set("coms1101@gmail.com")
//                        }
//                    }
//
//                    scm {
//                        connection.set("scm:git:git://github.com/comstering/eunoia.git")
//                        developerConnection.set("scm:git:ssh://github.com:comstering/eunoia.git")
//                        url.set("https://github.com/comstering/eunoia")
//                    }
//                }
//            }
//        }

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
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
