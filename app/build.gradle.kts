plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.gooleServices) // Firebase
    alias(libs.plugins.crashlytics) // Firebase
}

android {
    namespace = "com.amver.cultura_ayacucho"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.amver.cultura_ayacucho"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Coil para cargar imágenes
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Navigation compose para la navegación
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Retrofit para las peticiones HTTP
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)


    //Test Experimental
    implementation (libs.slf4j.simple)
// https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    testImplementation("ch.qos.logback:logback-classic:1.5.6")


    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation ("io.mockk:mockk:1.13.5")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")

    // Testing
    testImplementation (libs.mockito.junit.jupiter)

    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}