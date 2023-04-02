import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(dependency: Any) {
    add("implementation", dependency)
}

fun DependencyHandlerScope.androidTestImplementation(dependency: Any) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandlerScope.coreLibraryDesugaring(dependency: Any) {
    add("coreLibraryDesugaring", dependency)
}

fun DependencyHandlerScope.testImplementation(dependency: Any) {
    add("testImplementation", dependency)
}

fun DependencyHandlerScope.androidTestRuntimeOnly(dependency: Any) {
    add("androidTestRuntimeOnly", dependency)
}

fun DependencyHandlerScope.kapt(dependency: Any) {
    add("kapt", dependency)
}