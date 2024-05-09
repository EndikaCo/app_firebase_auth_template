// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    // ksp
    id ("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
}

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {

        //firebase
        classpath(libs.google.services)

        //firebase auth
        classpath(libs.google.services)
    }
}