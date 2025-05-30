plugins {
    `kotlin-dsl` // 이게 있어야 Plugin, Project import 가능
}

dependencies {
    implementation("com.android.tools.build:gradle:8.10.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.21")
    implementation("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.1.10")
}

gradlePlugin {
    plugins {
        register("jvmLibrary") {
            id = "com.sdhong.jonbeowin.jvm.library"
            implementationClass = "JvmLibraryPlugin"
        }
        register("androidLibrary") {
            id = "com.sdhong.jonbeowin.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidCompose") {
            id = "com.sdhong.jonbeowin.android.compose"
            implementationClass = "AndroidComposePlugin"
        }
        register("hilt") {
            id = "com.sdhong.jonbeowin.hilt"
            implementationClass = "HiltPlugin"
        }
        register("androidFeature") {
            id = "com.sdhong.jonbeowin.android.feature"
            implementationClass = "AndroidFeaturePlugin"
        }
    }
}
