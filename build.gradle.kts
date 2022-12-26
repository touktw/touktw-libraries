buildscript {
    extra["composeBomVersion"] = "2022.12.00"
    extra["compileSdkVersion"] = 33
    extra["minSdkVersion"] = 21
}

plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}