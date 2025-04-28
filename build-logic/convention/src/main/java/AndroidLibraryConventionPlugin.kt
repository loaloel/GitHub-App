import com.aloel.dev.github.convention.configureKotlinAndroid
import com.android.build.gradle.LibraryExtension
import com.yesdok.doctor.build_logic.convention.androidTestImplementation
import com.yesdok.doctor.build_logic.convention.libs
import com.yesdok.doctor.build_logic.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("kotlin-parcelize")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
            }

            dependencies {
                testImplementation(kotlin("test"))
                testImplementation(libs.findLibrary("junit").get())
                testImplementation(libs.findLibrary("mockito-core").get())
                testImplementation(libs.findLibrary("mockito-kotlin").get())
                testImplementation(libs.findLibrary("kotlinx-coroutines-test").get())
                testImplementation(libs.findLibrary("androidx-core-testing").get())
                testImplementation(libs.findLibrary("mockwebserver").get())
                testImplementation(libs.findLibrary("turbine").get())
                androidTestImplementation(libs.findLibrary("androidx-junit").get())
                androidTestImplementation(libs.findLibrary("androidx-espresso-core").get())
            }


        }
    }
}
