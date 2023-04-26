plugins {
    `kotlin-dsl`
}

group = "com.touktw.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "touktw.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "touktw.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "touktw.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "touktw.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidTest") {
            id = "touktw.android.test.instrument"
            implementationClass = "AndroidInstrumentTestConventionPlugin"
        }
        register("androidHilt") {
            id = "touktw.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("kotlinSerialization") {
            id = "touktw.kotlin.serialization"
            implementationClass = "KotlinSerializationPlugin"
        }
        register("androidLibraryTest") {
            id = "touktw.android.library.test"
            implementationClass = "AndroidLibraryTestConventionPlugin"
        }
        register("mavenPublish") {
            id = "touktw.maven.publish"
            implementationClass = "MavenPublishConventionPlugin"
        }
    }
}