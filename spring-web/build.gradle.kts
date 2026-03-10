plugins {}

dependencies {
    implementation(project(":exception"))

    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.boot.starter.web)
}
