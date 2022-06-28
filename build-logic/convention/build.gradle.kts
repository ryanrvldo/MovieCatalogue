plugins {
    `kotlin-dsl`
}

group = "com.ryanrvldo.movielibrary.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "movielibrary.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}