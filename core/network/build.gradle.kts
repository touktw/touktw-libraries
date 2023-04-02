plugins {
    id("touktw.android.library")
    id("touktw.android.hilt")
}

android {
    namespace = "com.touktw.core.network"

    dependencies {
        implementation(libs.timber)
        implementation(libs.bundles.network)
    }
}