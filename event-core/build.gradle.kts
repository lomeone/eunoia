val eventCoreVersion: String by project

version = eventCoreVersion

plugins {}

dependencies {
    // cloud event
    api(libs.cloudevents.kafka)
}
