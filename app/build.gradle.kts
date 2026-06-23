plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "cm.trixobase.camermeteo"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }
    androidResources {
        @Suppress("UnstableApiUsage")
        //noinspection MissingResourcesProperties
        generateLocaleConfig = true
    }
    defaultConfig {
        applicationId = "cm.trixobase.camermeteo"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        @Suppress("DEPRECATION")
        resourceConfigurations += listOf("fr", "ar", "de", "en", "es", "it", "zh")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Nav drawer
    implementation(libs.androidx.navigation.compose.android)

    // Google Fonts
    implementation(libs.androidx.compose.ui.text.google.fonts)

    // Web request
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Implement viewModel
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Image asynchronous
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.kotlinx.coroutines.android)

    // Background Work
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    implementation(project(":common"))

}