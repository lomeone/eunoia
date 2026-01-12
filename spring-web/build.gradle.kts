val springWebDgsVersion: String by project

version = springWebDgsVersion

plugins {}

dependencies {
    implementation(project(":exception"))

    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.boot.web.starter)
}
