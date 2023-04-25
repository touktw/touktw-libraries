plugins {
    id("touktw.android.library")
    id("touktw.android.library.test")
}

android {
    namespace = "com.touktw.architecture.mvi"


    dependencies {
        implementation(libs.androidx.lifecycle.viewmodel)
        implementation(libs.coroutines.android)
    }
}