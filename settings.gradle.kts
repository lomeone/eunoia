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
include("event-spring-transactional-outbox")
include("exception")
include("kafka-connect-smt")
include("kotlin-util")
include("optimize")
include("security")
include("spring-event-kafka")
include("spring-web")
