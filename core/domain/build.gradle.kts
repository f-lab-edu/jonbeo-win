plugins {
    alias(libs.plugins.jonbeowin.jvm.library)
    alias(libs.plugins.jonbeowin.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
