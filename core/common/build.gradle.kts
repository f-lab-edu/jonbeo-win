plugins {
    alias(libs.plugins.jonbeowin.android.library)
}

android {
    namespace = "com.sdhong.jonbeowin.core.common"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.runtime.ktx)
}