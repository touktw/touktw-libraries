package com.touktw

import androidTestImplementation
import com.android.build.api.dsl.CommonExtension
import implementation
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType


internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("androidxComposeCompiler").get().toString()
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            val uiBundle = libs.findBundle("androidx.compose.ui").get()
            implementation(platform(bom))
            implementation(uiBundle)
            androidTestImplementation(platform(bom))
        }
    }
}
