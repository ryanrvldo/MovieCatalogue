import dependencies.AnnotationProcessorDependencies
import dependencies.DebugDependencies
import dependencies.Dependencies
import dependencies.TestAndroidDependencies
import extensions.*

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.NAVIGATION_SAFE_ARGS)
    id(BuildPlugins.HILT)
    id(BuildPlugins.JUNIT5)
}

android {
    compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION
    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION
        buildToolsVersion = BuildAndroidConfig.BUILD_TOOLS_VERSION

        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME
        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            proguardFiles("proguard-androidt-optimize.txt", "proguard-rules.pro")
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled
        }

        getByName(BuildType.DEBUG) {
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
        }
    }

    buildTypes.forEach {
        try {
            it.buildConfigStringField("TMDB_API_KEY", getLocalProperty("TMDB_API_KEY"))
            it.buildConfigStringField("TMDB_BASE_URL", "https://api.themoviedb.org/3/")
            it.buildConfigStringField("TMDB_IMAGE_BASE_URL", "https://image.tmdb.org/t/p/w500")
            it.buildConfigStringField("TMDB_IMAGE_342", "https://image.tmdb.org/t/p/w342")
            it.buildConfigStringField("YOUTUBE_VIDEO_URL", "https://m.youtube.com/watch?v=%s")
            it.buildConfigStringField("YOUTUBE_THUMBNAIL_URL",
                "https://img.youtube.com/vi/%s/0.jpg")
        } catch (ignored: Exception) {
            throw Exception(
                "You should define 'TMDB_API_KEY' in local.properties. Visit 'https://www.themoviedb.org/' " +
                        "to obtain them."
            )
        }
    }

    dynamicFeatures.addAll(emptyList())

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xuse-experimental=kotlinx.coroutines.FlowPreview"
        )
    }

    lint {
        lintConfig = rootProject.file(".lint/config.xml")
        isCheckAllWarnings = true
        isWarningsAsErrors = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
        getByName("test") {
            java.srcDir("src/test/kotlin")
        }
        getByName("androidTest") {
            java.srcDir("src/androidTest/kotlin")
        }
    }
}

dependencies {
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.ACTIVITY_KTX)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.RECYCLER_VIEW)
    implementation(Dependencies.VIEW_PAGER)
    implementation(Dependencies.SWIPE_REFRESH_LAYOUT)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.CARD_VIEW)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.AUTO_IMAGE_SLIDER)
    implementation(Dependencies.SHIMMER)
    implementation(Dependencies.FIREBASE_ANALYTICS)
    implementation(Dependencies.FIREBASE_MESSAGING)
    implementation(Dependencies.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Dependencies.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Dependencies.LIFECYCLE_COMPILER)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER)
    implementation(Dependencies.OK_HTTP_LOGGING)
    implementation(Dependencies.NAVIGATION_FRAGMENT)
    implementation(Dependencies.NAVIGATION_UI)
    implementation(Dependencies.ROOM)
    implementation(Dependencies.COROUTINES)
    implementation(Dependencies.COROUTINES_ANDROID)
    implementation(Dependencies.GLIDE)
    implementation(Dependencies.HILT)
    implementation(Dependencies.TIMBER)
    implementation(TestAndroidDependencies.ESPRESSO_IDLING_RESOURCE)

    debugImplementation(DebugDependencies.LEAK_CANARY)

    kapt(AnnotationProcessorDependencies.ROOM)
    kapt(AnnotationProcessorDependencies.GLIDE)
    kapt(AnnotationProcessorDependencies.HILT)
}
