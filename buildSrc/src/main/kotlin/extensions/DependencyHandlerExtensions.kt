package extensions

import dependencies.DebugDependencies
import dependencies.TestAndroidDependencies
import dependencies.TestDependencies
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Adds a dependency to the `debugImplementation` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.debugImplementation(dependencyNotation: String): Dependency? =
    add("debugImplementation", dependencyNotation)

/**
 * Adds a dependency to the `implementation` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.implementation(dependencyNotation: String): Dependency? =
    add("implementation", dependencyNotation)

/**
 * Adds a dependency to the `api` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.api(dependencyNotation: String): Dependency? =
    add("api", dependencyNotation)

/**
 * Adds a dependency to the `kapt` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.kapt(dependencyNotation: String): Dependency? =
    add("kapt", dependencyNotation)

/**
 * Adds a dependency to the `testImplementation` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.testImplementation(dependencyNotation: String): Dependency? =
    add("testImplementation", dependencyNotation)

/**
 * Adds a dependency to the `testImplementation` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.testRuntimeOnly(dependencyNotation: String): Dependency? =
    add("testRuntimeOnly", dependencyNotation)

/**
 * Adds a dependency to the `androidTestImplementation` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.androidTestImplementation(dependencyNotation: String): Dependency? =
    add("androidTestImplementation", dependencyNotation)

/**
 * Adds all the unit tests dependencies to specific configuration.
 */
fun DependencyHandler.addTestDependencies() {
    testImplementation(TestDependencies.JUNIT5_API)
    testRuntimeOnly(TestDependencies.JUNIT5_ENGINE)
    testImplementation(TestDependencies.JUNIT5_PARAM)
    testImplementation(TestDependencies.ARCH_CORE)
    testImplementation(TestDependencies.COROUTINES)
    testImplementation(TestDependencies.MOCKK)
    testImplementation(TestDependencies.TRUTH)
}

fun DependencyHandler.addAndroidTestDependencies() {
    androidTestImplementation(TestDependencies.ARCH_CORE)
    androidTestImplementation(TestAndroidDependencies.CORE_KTX)
    androidTestImplementation(TestAndroidDependencies.ANDROID_RULES)
    androidTestImplementation(TestAndroidDependencies.NAVIGATION)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO_CORE)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO_IDLING_RESOURCE)
    debugImplementation(DebugDependencies.FRAGMENT_TESTING)
}
