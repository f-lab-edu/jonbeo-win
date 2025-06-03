plugins {
    alias(libs.plugins.jonbeowin.android.feature)
}

android {
    namespace = "com.sdhong.jonbeowin.feature.jonbeocount"
}

dependencies {
    implementation(libs.androidx.navigation.fragment)
}