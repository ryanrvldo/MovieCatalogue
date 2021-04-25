package dependencies

/**
 * Project test android dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object TestAndroidDependencies {
    const val FRAGMENT_TEST =
        "androidx.fragment:fragment-testing:${BuildDependenciesVersions.FRAGMENT_KTX}"
    const val NAVIGATION_TEST = "androidx.navigation:navigation-testing:${BuildDependenciesVersions.NAVIGATION}"
    const val MOCKITO = "org.mockito:mockito-core:${BuildDependenciesVersions.MOCKITO}"
    const val MOCKITO_ANDROID = "org.mockito:mockito-core:${BuildDependenciesVersions.MOCKITO}"
    const val ANDROID_CORE = "androidx.test:core:${BuildDependenciesVersions.ANDROID_TEST}"
    const val ANDROID_RULES = "androidx.test:rules:${BuildDependenciesVersions.ANDROID_TEST}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${BuildDependenciesVersions.ESPRESSO}"
    const val ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:${BuildDependenciesVersions.ESPRESSO}"
    const val ESPRESSO_INTENTS = "androidx.test.espresso:espresso-intents:${BuildDependenciesVersions.ESPRESSO}"
}
