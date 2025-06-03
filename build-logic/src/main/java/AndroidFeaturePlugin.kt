import com.android.build.gradle.LibraryExtension
import com.sdhong.jonbeowin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.sdhong.jonbeowin.android.library")
            apply(plugin = "com.sdhong.jonbeowin.android.compose")
            apply(plugin = "com.sdhong.jonbeowin.hilt")

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    viewBinding = true
                }
            }

            dependencies {
                "implementation"(project(":core:domain"))
                "implementation"(project(":core:common"))
                "implementation"(libs.findLibrary("androidx-core-ktx").get())
                "implementation"(libs.findLibrary("androidx-appcompat").get())
                "implementation"(libs.findLibrary("androidx-fragment-ktx").get())
            }
        }
    }
}