plugins {
    id("touktw.android.library")
    id("touktw.android.test.instrument")
    id("touktw.android.hilt")
}

android {
    namespace = "com.touktw.core.security"

    defaultConfig {
        minSdk = 23
    }

    dependencies {
        implementation(libs.androidx.security.crypto)
        implementation(libs.timber)
    }
}