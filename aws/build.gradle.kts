plugins {}

dependencies {
    implementation(project(":security"))

    api(platform(libs.aws.sdk.kotlin.bom))
    api(libs.aws.sdk.kotlin.kms)
    api(libs.aws.sdk.kotlin.secretsmanager.jvm)
    implementation(libs.aws.sdk.kotlin.sts)
}
