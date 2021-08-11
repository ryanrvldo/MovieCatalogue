package dependencies

/**
 * Project dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object Dependencies {
    // CORE
    const val CORE_KTX = "androidx.core:core-ktx:${BuildDependenciesVersions.ANDROID_CORE}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${BuildDependenciesVersions.APPCOMPAT}"
    const val ACTIVITY_KTX =
        "androidx.activity:activity-ktx:${BuildDependenciesVersions.ACTIVITY_KTX}"
    const val FRAGMENT_KTX =
        "androidx.fragment:fragment-ktx:${BuildDependenciesVersions.FRAGMENT_KTX}"
    const val COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${BuildDependenciesVersions.COROUTINES}"
    const val COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${BuildDependenciesVersions.COROUTINES}"
    const val HILT = "com.google.dagger:hilt-android:${BuildDependenciesVersions.HILT}"

    // VIEW
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${BuildDependenciesVersions.CONSTRAINT_LAYOUT}"
    const val RECYCLER_VIEW =
        "androidx.recyclerview:recyclerview:${BuildDependenciesVersions.RECYCLER_VIEW}"
    const val CARD_VIEW = "androidx.cardview:cardview:${BuildDependenciesVersions.CARD_VIEW}"
    const val MATERIAL =
        "com.google.android.material:material:${BuildDependenciesVersions.MATERIAL}"
    const val SWIPE_REFRESH_LAYOUT =
        "androidx.swiperefreshlayout:swiperefreshlayout:${BuildDependenciesVersions.SWIPE_REFRESH_LAYOUT}"
    const val AUTO_IMAGE_SLIDER =
        "com.github.smarteist:autoimageslider:${BuildDependenciesVersions.AUTO_IMAGE_SLIDER}"
    const val SHIMMER = "com.facebook.shimmer:shimmer:${BuildDependenciesVersions.SHIMMER}"
    const val GLIDE = "com.github.bumptech.glide:glide:${BuildDependenciesVersions.GLIDE}"

    // JETPACK
    const val VIEW_PAGER = "androidx.viewpager2:viewpager2:${BuildDependenciesVersions.VIEW_PAGER}"
    const val LIFECYCLE_VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${BuildDependenciesVersions.LIFECYCLE}"
    const val LIFECYCLE_LIVE_DATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${BuildDependenciesVersions.LIFECYCLE}"
    const val LIFECYCLE_COMPILER =
        "androidx.lifecycle:lifecycle-common-java8:${BuildDependenciesVersions.LIFECYCLE}"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:${BuildDependenciesVersions.NAVIGATION}"
    const val NAVIGATION_UI =
        "androidx.navigation:navigation-ui-ktx:${BuildDependenciesVersions.NAVIGATION}"
    const val ROOM = "androidx.room:room-ktx:${BuildDependenciesVersions.ROOM}"

    // NETWORK
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${BuildDependenciesVersions.RETROFIT}"
    const val RETROFIT_CONVERTER =
        "com.squareup.retrofit2:converter-gson:${BuildDependenciesVersions.RETROFIT}"
    const val OK_HTTP_LOGGING =
        "com.squareup.okhttp3:logging-interceptor:${BuildDependenciesVersions.OK_HTTP_LOGGING}"
    const val MOSHI = "com.squareup.moshi:moshi:${BuildDependenciesVersions.MOSHI}"

    // FIREBASE
    const val FIREBASE_ANALYTICS =
        "com.google.firebase:firebase-analytics:${BuildDependenciesVersions.FIREBASE_ANALYTICS}"
    const val FIREBASE_MESSAGING =
        "com.google.firebase:firebase-messaging:${BuildDependenciesVersions.FIREBASE_MESSAGING}"

    // UTIL
    const val TIMBER = "com.jakewharton.timber:timber:${BuildDependenciesVersions.TIMBER}"
}
