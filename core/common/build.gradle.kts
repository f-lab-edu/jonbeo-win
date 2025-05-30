plugins {
    id("com.sdhong.jonbeowin.android.library")
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
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.navigation.runtime.ktx)
}