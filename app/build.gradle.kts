import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val tmdbApiKey: String = localProperties.getProperty("TMDB_API_KEY")
    ?: throw Exception("'TMDB_API_KEY' not found in local.properties. Please add it.")


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("kotlin-parcelize")
}

android {
    namespace = "com.byteberserker.movieshorts"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.byteberserker.movieshorts"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            val tmdbApiKey = localProperties.getProperty("TMDB_API_KEY")
                ?: throw Exception("'TMDB_API_KEY' not found in local.properties. Please add it.")

            // Option 1: Use resValue (Recommended for security)
            // Creates a string resource accessible via R.string.tmdb_api_key
           // resValue("string", "tmdb_api_key", tmdbApiKey)
            buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
        }

        debug {
            isMinifyEnabled = false
            val tmdbApiKey = localProperties.getProperty("TMDB_API_KEY")
                ?: throw Exception("'TMDB_API_KEY' not found in local.properties. Please add it.")

            // Option 1: Use resValue (Recommended for security)
            // Creates a string resource accessible via R.string.tmdb_api_key
            // resValue("string", "tmdb_api_key", tmdbApiKey)
            buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    android.buildFeatures.buildConfig = true
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.dagger.core)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel.savedstate)
    implementation(libs.lifecycle.common)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.common)
    kapt(libs.dagger.compiler)
    implementation(libs.glide.core)
    kapt(libs.glide.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}