package dependencies

/**
 * Project test dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object TestDependencies {
    const val ROOM = "androidx.room:room-testing:${BuildDependenciesVersions.ROOM}"
    const val JUNIT = "junit:junit:${BuildDependenciesVersions.JUNIT}"
    const val ARCH_CORE = "androidx.arch.core:core-testing:${BuildDependenciesVersions.TESTING_CORE}"
    const val MOCKITO = "org.mockito:mockito-core:${BuildDependenciesVersions.MOCKITO}"
}
