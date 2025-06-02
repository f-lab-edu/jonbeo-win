plugins {
    alias(libs.plugins.jonbeowin.android.feature)
}

android {
    namespace = "com.sdhong.jonbeowin.feature.asset"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.material)

    implementation(libs.androidx.navigation.fragment)
}