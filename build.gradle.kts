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
        classpath("com.google.gms:google-services:4.4.1")

        //firebase auth
        classpath("com.google.gms:google-services:4.4.1")
    }
}