buildscript {
    repositories {
        google()
    }
    dependencies {
        val navVersion = "2.7.5"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        // Add the Hilt Gradle plugin dependency
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48.1")
    }
}

plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
//    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
//    id("com.google.dagger.hilt.android") version "2.44" apply false
}