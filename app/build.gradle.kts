plugins {
    alias(libs.plugins.jonbeowin.android.application)
    alias(libs.plugins.jonbeowin.hilt)
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.sdhong.jonbeowin"

    defaultConfig {
        applicationId = "com.sdhong.jonbeowin"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.local)
    implementation(projects.core.remote)
    implementation(projects.feature.asset)
    implementation(projects.feature.encourage)
    implementation(projects.feature.jonbeocount)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
}