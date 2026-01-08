val awsUtilsVersion: String by project

val awsKotlinSdkVersion: String by project

version = awsUtilsVersion

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
