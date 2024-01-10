import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.gradle)
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.juanroig.composecourse.data"
    compileSdk = rootProject.extra["compile"] as Int?

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int?

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
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

            buildConfigField("String", "URL_BASE", "\"${localProperties.getProperty("url_base")}\"")
            buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("api_key")}\"")
        }
        debug {
            buildConfigField("String", "URL_BASE", "\"${localProperties.getProperty("url_base")}\"")
            buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("api_key")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
