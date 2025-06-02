plugins {
    alias(libs.plugins.jonbeowin.android.library)
    alias(libs.plugins.jonbeowin.hilt)
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "com.sdhong.jonbeowin.core.remote"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.data)

    implementation(libs.generativeai)
}