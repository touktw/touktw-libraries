plugins {
    id("touktw.android.library")
}

android {
    namespace = "com.touktw.architecture.mvi"


    dependencies {
        implementation(libs.androidx.lifecycle.viewmodel)
        implementation(libs.coroutines.android)
    }
}