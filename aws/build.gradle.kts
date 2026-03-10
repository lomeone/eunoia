plugins {}

dependencies {
    implementation(project(":exception"))
    implementation(project(":security"))

    implementation(platform(libs.kotlinx.serialization.bom))
    implementation(libs.kotlinx.serialization.json)

    api(platform(libs.aws.sdk.kotlin.bom))
    api(libs.aws.sdk.kotlin.kms)
    api(libs.aws.sdk.kotlin.secretsmanager.jvm)
    implementation(libs.aws.sdk.kotlin.sts)
}
