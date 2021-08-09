plugins {
    id("commons.android-library")
}

dependencies {
    api(project(":data"))
    api(project(":domain"))
}
