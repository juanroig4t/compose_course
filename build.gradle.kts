// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.android.library) apply false
}

ext {
    extra["compile"] = 34
    extra["target"] = 34
    extra["minSdk"] = 24
    extra["build"] = "34.0.0"
}

subprojects {
    afterEvaluate {
        project.apply(from = "${project.rootDir}/gradle/spotless.gradle")
    }
}