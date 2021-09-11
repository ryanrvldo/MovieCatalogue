import dependencies.Dependencies
import dependencies.TestDependencies

plugins {
    id("commons.android-library")
}

dependencies {
    implementation(Dependencies.PAGING_KTX)

    testImplementation(TestDependencies.PAGING_KTX)
    testRuntimeOnly(project(BuildModules.DATA))
}