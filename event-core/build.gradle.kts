val eventCorePackageVersion: String by project

version = eventCorePackageVersion

plugins {}

dependencies {
    // cloud event
    api(libs.cloudevents.kafka)
}
