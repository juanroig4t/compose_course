// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.spotless)
}

ext {
    extra["compile"] = 36
    extra["target"] = 36
    extra["minSdk"] = 26
    extra["build"] = "36.0.0"
}

subprojects {
    afterEvaluate {
        project.apply(from = "${project.rootDir}/gradle/spotless.gradle")
    }
}
