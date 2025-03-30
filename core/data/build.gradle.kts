plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
    implementation(libs.kotlinx.coroutines.core)
}
