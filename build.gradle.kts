buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
        // ... el resto de classpath (Android Gradle plugin, Kotlin, etc.)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}