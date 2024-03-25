buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.navigation.safeArgs.plugin)
        classpath(libs.secrets.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
}