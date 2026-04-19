plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt.gradle) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.spotless)
}

subprojects {
    apply(from = rootProject.file("gradle/spotless.gradle"))
}
