plugins {
    alias(libs.plugins.jonbeowin.jvm.library)
    alias(libs.plugins.jonbeowin.hilt)
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.kotlinx.coroutines.core)
}
