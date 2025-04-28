plugins {
    alias(libs.plugins.aloel.dev.github.application)
    alias(libs.plugins.aloel.dev.github.hilt)
}

android {
    namespace = "com.aloel.dev.github.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aloel.dev.github.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core:models"))
    implementation(project(":core:networks"))

    implementation(project(":features:home"))

    implementation(libs.converter.gson)
}