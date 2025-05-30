plugins {
    id("com.sdhong.jonbeowin.android.library")
    id("com.sdhong.jonbeowin.hilt")
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "com.sdhong.jonbeowin.core.remote"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:data"))

    implementation(libs.generativeai)
}