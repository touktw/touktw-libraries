plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.junit5) apply false
    alias(libs.plugins.android.hilt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

group = "com.touktw.core"
version = "1.0.0-SNAPSHOTS"