plugins {
    alias(libs.plugins.aloel.dev.github.library)
    alias(libs.plugins.aloel.dev.github.hilt)
}

android {
    namespace = "com.aloel.dev.github.core.networks"
}

dependencies {
    implementation(project(":core:models"))

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}