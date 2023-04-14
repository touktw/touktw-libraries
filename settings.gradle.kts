pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "Touktw Libraries"
include(":app")

includeBuild("core") {
    dependencySubstitution {
        createSubstitutes(
            moduleName = "com.touktw.core",
            "network", "security"
        )
    }
}
includeBuild("design") {
    dependencySubstitution {
        createSubstitutes(
            moduleName = "com.touktw.design",
            "base", "widget"
        )
    }
}
includeBuild("architecture") {
    dependencySubstitution {
        createSubstitutes(
            moduleName = "com.touktw.architecture",
            "mvi"
        )
    }
}

fun DependencySubstitutions.createSubstitutes(
    moduleName: String,
    vararg projectNames: String,
) {
    projectNames.forEach { projectName ->
        substitute(module("$moduleName:$projectName")).using(project(":$projectName"))
    }
}