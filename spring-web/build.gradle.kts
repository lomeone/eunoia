val springWebDgsVersion: String by project

version = springWebDgsVersion

plugins {}

dependencies {
    implementation(project(":exception"))

    implementation(platform(libs.spring.framework.bom))
    implementation(libs.spring.framework.web)
}
