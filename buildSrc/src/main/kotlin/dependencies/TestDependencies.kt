package dependencies

import BuildDependenciesVersions

/**
 * Project test dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object TestDependencies {
    const val JUNIT5_API =
        "org.junit.jupiter:junit-jupiter-api:${BuildDependenciesVersions.Test.JUNIT5}"
    const val JUNIT5_ENGINE =
        "org.junit.jupiter:junit-jupiter-engine:${BuildDependenciesVersions.Test.JUNIT5}"
    const val JUNIT5_PARAM =
        "org.junit.jupiter:junit-jupiter-params:${BuildDependenciesVersions.Test.JUNIT5}"
    const val ARCH_CORE =
        "androidx.arch.core:core-testing:${BuildDependenciesVersions.Test.ANDROIDX_TESTING_CORE}"
    const val ROOM = "androidx.room:room-testing:${BuildDependenciesVersions.ROOM}"
    const val COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${BuildDependenciesVersions.COROUTINES}"
    const val MOCKK = "io.mockk:mockk:${BuildDependenciesVersions.Test.MOCKK}"
    const val MOCK_WEB_SERVER =
        "com.squareup.okhttp3:mockwebserver:${BuildDependenciesVersions.Test.MOCK_WEB_SERVER}"
    const val TRUTH = "com.google.truth:truth:${BuildDependenciesVersions.Test.TRUTH}"
    const val PAGING_KTX = "androidx.paging:paging-common-ktx:${BuildDependenciesVersions.PAGING}"
}
