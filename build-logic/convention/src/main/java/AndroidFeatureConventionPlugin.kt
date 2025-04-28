import com.aloel.dev.github.convention.configureKotlinAndroid
import com.android.build.gradle.LibraryExtension
import com.yesdok.doctor.build_logic.convention.androidTestImplementation
import com.yesdok.doctor.build_logic.convention.implementation
import com.yesdok.doctor.build_logic.convention.libs
import com.yesdok.doctor.build_logic.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("aloel.dev.github.library")
                apply("aloel.dev.github.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("androidx.navigation.safeargs.kotlin")
            }

            dependencies {
                implementation(project(":core:design-system:icons"))
                implementation(project(":core:extensions"))
                implementation(project(":core:models"))
                implementation(project(":core:networks"))

                implementation(libs.findLibrary("glide").get())

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
