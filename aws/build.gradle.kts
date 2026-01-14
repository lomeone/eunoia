val awsPackageVersion: String by project

version = awsPackageVersion

plugins {}

dependencies {
    implementation(project(":security"))

    api(platform(libs.aws.bom))
    api(libs.aws.kms)
    implementation(libs.aws.sts)
}
