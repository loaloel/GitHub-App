package com.aloel.dev.github.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val apiBaseUrl = "https://api.github.com/"
val personalToken = "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"

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 35

        defaultConfig {
            minSdk = 26

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        buildFeatures {
            viewBinding = true
            buildConfig = true
        }

        buildTypes {
            defaultConfig {
                buildConfigField("String", "BASE_URL","\"$apiBaseUrl\"")
                buildConfigField("String", "PERSONAL_TOKEN","\"$personalToken\"")
            }

        }

        packaging {
            resources.excludes.add("/META-INF/DEPENDENCIES")
            resources.excludes.add("/META-INF/LICENSE")
            resources.excludes.add("/META-INF/LICENSE.txt")
            resources.excludes.add("/META-INF/license.txt")
            resources.excludes.add("/META-INF/NOTICE")
            resources.excludes.add("/META-INF/NOTICE.txt")
            resources.excludes.add("/META-INF/notice.txt")
            resources.excludes.add("/META-INF/ASL2.0")
            resources.excludes.add("/META-INF/*.kotlin_module")
        }
    }

    configureKotlin()
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
