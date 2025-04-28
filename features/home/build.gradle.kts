plugins {
    alias(libs.plugins.aloel.dev.github.feature)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.aloel.dev.github.features.home"
}

dependencies {
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.browser)
    implementation(libs.converter.gson)
}