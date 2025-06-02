plugins {
    alias(libs.plugins.jonbeowin.android.application)
    alias(libs.plugins.jonbeowin.hilt)
    alias(libs.plugins.secrets.gradle.plugin)
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
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:local"))
    implementation(project(":core:remote"))
    implementation(project(":feature:asset"))
    implementation(project(":feature:encourage"))
    implementation(project(":feature:jonbeocount"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)

    implementation(libs.timber)

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
}