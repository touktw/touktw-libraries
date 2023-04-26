plugins {
    id("touktw.android.library")
    id("touktw.android.library.test")
    id("touktw.maven.publish")
}

android {
    namespace = "com.touktw.architecture.mvi"

    dependencies {
        implementation(libs.androidx.lifecycle.viewmodel)
        implementation(libs.coroutines.android)
    }
}

publishing {
    publications {
        getByName<MavenPublication>("release") {
            artifactId = "mvi"
            pom {
                name.set("architecture-mvi")
                description.set("architecture base for mvi")
            }
        }
    }
}

