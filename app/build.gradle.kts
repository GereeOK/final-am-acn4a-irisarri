// build.gradle.kts (app)

plugins {
    // Plugin de aplicación Android
    id("com.android.application")
    // Plugin de Kotlin (si tu proyecto sólo es Java puedes omitir esta línea)
    kotlin("android") version "1.8.0" apply false
}

android {
    namespace = "geremiasirisarri.aplicacionmoviles"
    compileSdk = 35

    defaultConfig {
        applicationId = "geremiasirisarri.aplicacionmoviles"
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    // Si de verdad no usas Kotlin en el módulo, quita completamente el bloque kotlinOptions / kotlin(…)
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.activity:activity:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
