plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://plugins.gradle.org/m2/")
    maven("https://ci.android.com/builds/submitted/5837096/androidx_snapshot/latest/repository")
}

object PluginsVersions {
    const val GRADLE_ANDROID = "7.0.0"
    const val GRADLE_VERSIONS = "0.38.0"
    const val KOTLIN = "1.5.21"
    const val NAVIGATION = "2.3.5"
    const val HILT = "2.38.1"
    const val DOKKA = "0.10.1"
    const val KTLINT = "0.42.1"
    const val SPOTLESS = "5.12.4"
    const val DETEKT = "1.16.0"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE_ANDROID}")
    implementation("com.github.ben-manes:gradle-versions-plugin:${PluginsVersions.GRADLE_VERSIONS}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:${PluginsVersions.NAVIGATION}")
    implementation("com.google.dagger:hilt-android-gradle-plugin:${PluginsVersions.HILT}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${PluginsVersions.DOKKA}")
    implementation("com.pinterest:ktlint:${PluginsVersions.KTLINT}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${PluginsVersions.SPOTLESS}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginsVersions.DETEKT}")
}
