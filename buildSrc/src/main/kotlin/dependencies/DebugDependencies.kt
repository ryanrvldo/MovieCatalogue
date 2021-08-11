package dependencies

/**
 * Project debug dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object DebugDependencies {
    const val LEAK_CANARY =
        "com.squareup.leakcanary:leakcanary-android:${BuildDependenciesVersions.LEAK_CANARY}"
    const val FRAGMENT_TESTING =
        "androidx.fragment:fragment-testing:${BuildDependenciesVersions.FRAGMENT_KTX}"
}
