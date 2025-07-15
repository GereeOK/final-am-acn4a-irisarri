// app/build.gradle.kts

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")    // ← Inyecta google-services.json
}

android {
    namespace   = "com.geremiasirisarri.aplicacionesmoviles"
    compileSdk  = 33

    defaultConfig {
        applicationId         = "com.geremiasirisarri.aplicacionesmoviles"
        minSdk                = 24
        targetSdk             = 33
        versionCode           = 1
        versionName           = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core Android
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // ─── Firebase via BoM (unifica versiones) ────────────────────
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))

    // Auth
    implementation("com.google.firebase:firebase-auth-ktx")
    // Firestore
    implementation("com.google.firebase:firebase-firestore-ktx")
    // ───────────────────────────────────────────────────────────────

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}