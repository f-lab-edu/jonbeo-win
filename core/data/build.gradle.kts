plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.hilt.core)
    implementation(libs.kotlinx.coroutines.core)
}
