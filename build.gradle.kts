// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.spotless)
}

ext {
    extra["compile"] = 34
    extra["target"] = 34
    extra["minSdk"] = 26
    extra["build"] = "34.0.0"
}

subprojects {
    afterEvaluate {
        project.apply(from = "${project.rootDir}/gradle/spotless.gradle")
    }
}