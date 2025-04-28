import com.aloel.dev.github.convention.configureKotlinAndroid
import com.android.build.api.dsl.ApplicationExtension
import com.yesdok.doctor.build_logic.convention.androidTestImplementation
import com.yesdok.doctor.build_logic.convention.implementation
import com.yesdok.doctor.build_logic.convention.libs
import com.yesdok.doctor.build_logic.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("kotlin-android")
                apply("kotlin-parcelize")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
            }

            dependencies {
                implementation(libs.findLibrary("androidx-core-ktx").get())
                implementation(libs.findLibrary("androidx-lifecycle-viewmodel-ktx").get())
                implementation(libs.findLibrary("androidx-appcompat").get())
                implementation(libs.findLibrary("androidx-activity-ktx").get())
                implementation(libs.findLibrary("material").get())
                testImplementation(libs.findLibrary("junit").get())
                testImplementation(libs.findLibrary("mockito-core").get())
                testImplementation(libs.findLibrary("mockito-kotlin").get())
                testImplementation(libs.findLibrary("kotlinx-coroutines-test").get())
                testImplementation(libs.findLibrary("androidx-core-testing").get())
                testImplementation(libs.findLibrary("mockwebserver").get())
                testImplementation(libs.findLibrary("turbine").get())
                androidTestImplementation(libs.findLibrary("androidx-junit").get())
                androidTestImplementation(libs.findLibrary("androidx-espresso-core").get())
                androidTestImplementation(libs.findLibrary("androidx-espresso-contrib").get())

            }
        }
    }
}
