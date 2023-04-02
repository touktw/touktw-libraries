plugins {
    id("touktw.android.library")
    id("touktw.android.library.compose")
}

android {
    namespace = "com.touktw.design.base"

    dependencies {
        implementation(libs.androidx.compose.material)
        implementation(libs.androidx.compose.foundation)
        debugImplementation(libs.androidx.compose.ui.tooling)
    }
}