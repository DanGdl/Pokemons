// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("kotlin_version", "1.9.0")
        set("lifecycle_version", "2.6.2")
        set("rxbinding3", "3.1.0")
        set("nav_version", "2.7.2")
        set("work_version", "2.8.1")
        set("room", "2.5.2")
        set("room_compiler", "2.5.2")
        set("rx_java", "3.1.6")
        set("rx_android", "3.0.2")
        set("recycler", "1.3.1")
        set("ver_compat", "1.6.1")
        set("gson", "2.9.0")
        set("guava", "28.0-android")
        set("retrofit", "2.9.0")
        set("retrofit_gson", "2.9.0")
        set("retrofit_rx", "2.9.0")
        set("okhttp_log", "4.10.0")
        set("okhttp", "4.10.0")
        set("ver_junit", "4.13.2")
        set("junit_android", "1.1.5")
        set("espresso", "3.5.1")
    }

    project.extra.apply {
        set("min", 20)
        set("target", 34)
        set("compile", 34)
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("androidx.navigation.safeargs") version "2.7.2" apply false
    id("com.android.library") version "8.1.1" apply false
}


