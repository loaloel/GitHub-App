plugins {
    alias(libs.plugins.aloel.dev.github.library)
}

android {
    namespace = "com.aloel.dev.github.core.models"
}

dependencies {
    implementation(libs.converter.gson)
}