plugins {
    id("com.sdhong.jonbeowin.android.feature")
}

android {
    namespace = "com.sdhong.jonbeowin.feature.encourage"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.material)
}