plugins {
    `kotlin-dsl` // 이게 있어야 Plugin, Project import 가능
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.android.gradlePlugin)
    implementation(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("jvmLibrary") {
            id = libs.plugins.jonbeowin.jvm.library.get().pluginId
            implementationClass = "JvmLibraryPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.jonbeowin.android.library.get().pluginId
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidCompose") {
            id = libs.plugins.jonbeowin.android.compose.get().pluginId
            implementationClass = "AndroidComposePlugin"
        }
        register("androidFeature") {
            id = libs.plugins.jonbeowin.android.feature.get().pluginId
            implementationClass = "AndroidFeaturePlugin"
        }
        register("hilt") {
            id = libs.plugins.jonbeowin.hilt.get().pluginId
            implementationClass = "HiltPlugin"
        }
        register("androidApplication") {
            id = libs.plugins.jonbeowin.android.application.get().pluginId
            implementationClass = "AndroidApplicationPlugin"
        }
    }
}
