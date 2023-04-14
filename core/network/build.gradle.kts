plugins {
    id("touktw.android.library")
    id("touktw.android.hilt")
    id("touktw.kotlin.serialization")
}

android {
    namespace = "com.touktw.core.network"

    dependencies {
        implementation(libs.timber)
        implementation(libs.bundles.network)
    }
}