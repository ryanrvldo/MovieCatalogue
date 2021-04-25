package dependencies

/**
 * Project annotation processor dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object AnnotationProcessorDependencies {
    const val ROOM = "androidx.room:room-compiler:${BuildDependenciesVersions.ROOM}"
    const val GLIDE =
        "com.github.bumptech.glide:compiler:${BuildDependenciesVersions.GLIDE}"
    const val HILT =
        "com.google.dagger:hilt-compiler:${BuildDependenciesVersions.HILT}"
    const val HILT_ANDROID =
        "androidx.hilt:hilt-compiler:${BuildDependenciesVersions.HILT_ANDROID}"
    const val DATA_BINDING = "com.android.databinding:compiler:${BuildDependenciesVersions.DATA_BINDING}"
}
