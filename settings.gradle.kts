pluginManagement {
    repositories {
        google()            // para resolver los plugins de Android y Firebase
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // Declara aquí las versiones de los plugins para aplicar en cada módulo
        id("com.android.application") version "7.4.2" apply false
        id("com.google.gms.google-services") version "4.3.15" apply false
        kotlin("android") version "1.8.0" apply false
    }
}

dependencyResolutionManagement {
    // Usamos PREFER_SETTINGS para que sólo los repositorios declarados aquí sirvan
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "geremias-irisarri-aplicacionesmoviles"
include(":app")
