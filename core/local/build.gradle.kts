plugins {
    id("com.sdhong.jonbeowin.android.library")
    id("com.sdhong.jonbeowin.hilt")
}

android {
    namespace = "com.sdhong.jonbeowin.core.local"
}

dependencies {
    implementation(project(":core:data"))

    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}