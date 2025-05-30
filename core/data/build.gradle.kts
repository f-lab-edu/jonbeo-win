plugins {
    id("com.sdhong.jonbeowin.jvm.library")
    id("com.sdhong.jonbeowin.hilt")
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.kotlinx.coroutines.core)
}
