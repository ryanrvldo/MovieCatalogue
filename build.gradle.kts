import extensions.applyDefault
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins.apply(BuildPlugins.UPDATE_DEPENDENCIES)

allprojects {
    repositories.applyDefault()

    plugins.apply(BuildPlugins.DETEKT)
    plugins.apply(BuildPlugins.DOKKA)
    plugins.apply(BuildPlugins.KTLINT)
    plugins.apply(BuildPlugins.SPOTLESS)

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events(PASSED, FAILED, SKIPPED)
        }
    }
}
