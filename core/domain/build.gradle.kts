plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.hilt.core)
    implementation(libs.kotlinx.coroutines.core)
}
