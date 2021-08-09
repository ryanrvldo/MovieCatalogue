import dependencies.AnnotationProcessorDependencies
import dependencies.Dependencies
import dependencies.TestDependencies
import extensions.buildConfigStringField
import extensions.getLocalProperty
import extensions.implementation
import extensions.testImplementation

plugins {
    id("commons.android-library")
}

android {
    buildTypes.forEach {
        try {
            it.buildConfigStringField("TMDB_API_KEY", getLocalProperty("TMDB_API_KEY"))
            it.buildConfigStringField("TMDB_BASE_URL", "https://api.themoviedb.org/3/")
            it.buildConfigStringField("TMDB_IMAGE_BASE_URL", "https://image.tmdb.org/t/p/w500")
            it.buildConfigStringField("TMDB_IMAGE_342", "https://image.tmdb.org/t/p/w342")
            it.buildConfigStringField("YOUTUBE_VIDEO_URL", "https://m.youtube.com/watch?v=%s")
            it.buildConfigStringField(
                "YOUTUBE_THUMBNAIL_URL",
                "https://img.youtube.com/vi/%s/0.jpg"
            )
        } catch (ignored: Exception) {
            throw InvalidUserDataException(
                "You should define 'TMDB_API_KEY' in local.properties. Visit 'https://www.themoviedb.org/' " +
                        "to obtain them."
            )
        }
    }
}

dependencies {
    implementation(Dependencies.ROOM)
    kapt(AnnotationProcessorDependencies.ROOM)

    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER)
    implementation(Dependencies.OK_HTTP_LOGGING)
    implementation(Dependencies.MOSHI)

    testImplementation(TestDependencies.MOCK_WEB_SERVER)
}