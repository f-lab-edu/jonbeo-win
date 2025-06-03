plugins {
    alias(libs.plugins.jonbeowin.android.feature)
}

android {
    namespace = "com.sdhong.jonbeowin.feature.jonbeocount"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.androidx.navigation.fragment)
}