val projectName: String by settings
rootProject.name = projectName

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        maven("https://packages.confluent.io/maven/")
    }
}

include("aws")
include("event-core")
include("exception")
include("kotlin-util")
include("optimization")
include("security")
include("spring-event-kafka")
include("spring-web")
