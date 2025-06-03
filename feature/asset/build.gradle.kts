plugins {
    alias(libs.plugins.jonbeowin.android.feature)
}

android {
    namespace = "com.sdhong.jonbeowin.feature.asset"
}

dependencies {
    implementation(libs.androidx.navigation.fragment)
}