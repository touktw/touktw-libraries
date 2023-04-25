import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val extension = extensions.getByType<LibraryExtension>()
            configureKotest(extension)

        }
    }
}

private fun Project.configureKotest(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    commonExtension.apply {
        testOptions {
            unitTests.all { test ->
                test.useJUnitPlatform()
            }
        }
        dependencies {
            val testBundle = libs.findBundle("test").get()
            testImplementation(testBundle)
        }
    }
}