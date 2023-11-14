// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.spotless)
}

subprojects {
    afterEvaluate {
        project.apply(from = "${project.rootDir}/gradle/spotless.gradle")
    }
}