package dependencies

/**
 * Project test android dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object TestAndroidDependencies {
    const val ANDROID_CORE =
        "androidx.test:core-ktx:${BuildDependenciesVersions.Test.ANDROIDX_TEST}"
    const val ANDROID_RULES = "androidx.test:rules:${BuildDependenciesVersions.Test.ANDROIDX_TEST}"
    const val FRAGMENT_TEST =
        "androidx.fragment:fragment-testing:${BuildDependenciesVersions.FRAGMENT_KTX}"
    const val NAVIGATION_TEST =
        "androidx.navigation:navigation-testing:${BuildDependenciesVersions.NAVIGATION}"
    const val ESPRESSO =
        "androidx.test.espresso:espresso-core:${BuildDependenciesVersions.Test.ANDROIDX_ESPRESSO}"
    const val ESPRESSO_IDLING_RESOURCE =
        "androidx.test.espresso:espresso-idling-resource:${BuildDependenciesVersions.Test.ANDROIDX_ESPRESSO}"
}
