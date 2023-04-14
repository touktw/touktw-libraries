plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.junit5) apply false
    alias(libs.plugins.android.hilt) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

allprojects {
    if (this == rootProject) {
        return@allprojects
    }
    apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
    extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint("0.46.1").editorConfigOverride(
                mapOf(
                    "ktlint_code_style" to "android",
                    "ij_kotlin_allow_trailing_comma" to true,
                    "max_line_length" to "120",
                    // These rules were introduced in ktlint 0.46.0 and should not be
                    // enabled without further discussion. They are disabled for now.
                    // See: https://github.com/pinterest/ktlint/releases/tag/0.46.0
                    "disabled_rules" to
                            "filename," +
                            "annotation,annotation-spacing," +
                            "argument-list-wrapping," +
                            "double-colon-spacing," +
                            "enum-entry-name-case," +
                            "multiline-if-else," +
                            "no-empty-first-line-in-method-block," +
                            "package-name," +
                            "trailing-comma," +
                            "spacing-around-angle-brackets," +
                            "spacing-between-declarations-with-annotations," +
                            "spacing-between-declarations-with-comments," +
                            "unary-op-spacing"
                )
            )
            licenseHeaderFile(rootProject.file("copyright.kt"))
        }
        format("kts") {
            target("**/*.kts")
            targetExclude("**/build/**/*.kts")
            licenseHeaderFile(rootProject.file("copyright.kt"), "(^(?![\\/ ]\\*).*$)")
        }
    }
}