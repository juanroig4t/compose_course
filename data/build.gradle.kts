import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.gradle)
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

fun resolvedProperty(name: String): String {
    return providers.gradleProperty(name).orNull
        ?: localProperties.getProperty(name, "")
}

android {
    namespace = "com.juanroig.composecourse.data"
    compileSdk = property("android.compileSdk").toString().toInt()

    defaultConfig {
        minSdk = property("android.minSdk").toString().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "URL_BASE", "\"${resolvedProperty("url_base")}\"")
            buildConfigField("String", "API_KEY", "\"${resolvedProperty("api_key")}\"")
        }
        debug {
            buildConfigField("String", "URL_BASE", "\"${resolvedProperty("url_base")}\"")
            buildConfigField("String", "API_KEY", "\"${resolvedProperty("api_key")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    // Room
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // DataStore
    implementation(libs.dataStore.core)
    implementation(libs.dataStore.preferences)

    // Retrofit
    implementation(libs.okhttp.logging)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.serialization)

//    implementation(libs.retrofit.kotlin.serialization)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
