import com.android.build.gradle.LibraryExtension
import com.sdhong.jonbeowin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    compose = true
                }
            }

            dependencies {
                val bom = libs.findLibrary("androidx-compose-bom").get()
                "implementation"(platform(bom))
                "androidTestImplementation"(platform(bom))
                "implementation"(libs.findLibrary("androidx-material3").get())
                "implementation"(libs.findLibrary("androidx-ui-tooling-preview").get())
                "debugImplementation"(libs.findLibrary("androidx-ui-tooling").get())
            }
        }
    }
}