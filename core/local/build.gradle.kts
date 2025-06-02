plugins {
    alias(libs.plugins.jonbeowin.android.library)
    alias(libs.plugins.jonbeowin.hilt)
}

android {
    namespace = "com.sdhong.jonbeowin.core.local"
}

dependencies {
    implementation(projects.core.data)

    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}