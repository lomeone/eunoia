val awsModuleVersion: String by project

version = awsModuleVersion

plugins {}

dependencies {
    implementation(project(":security"))

    api(platform(libs.aws.bom))
    api(libs.aws.kms)
    implementation(libs.aws.sts)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
