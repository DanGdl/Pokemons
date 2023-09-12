// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("kotlin_version", "1.8.20") // update according to composeVersion
        set("lifecycle_ktx", "2.6.1")
        set("nav_version", "2.7.2")
        set("work_version", "2.8.1")
        set("room", "2.5.2")
        set("room_compiler", "2.5.2")
        set("gson", "2.9.0")
        set("retrofit", "2.9.0")
        set("retrofit_gson", "2.9.0")
        set("okhttp_log", "4.10.0")
        set("okhttp", "4.10.0")
        set("ver_junit", "4.13.2")
        set("junit_android", "1.1.5")
        set("espresso", "3.5.1")

        set("hilt", "2.46.1")
        set("hilt_jetpack", "1.0.0")
        set("testing_core", "1.1.1")
        set("testing_coroutine", "1.6.0")

        // update kotlinCompilerExtensionVersion when composeVersion updated
        // update kotlinCompilerExtensionVersion when composeVersionTheme updated
        set("composeVersion", "1.4.2")
        set("composeVersionThemeMaterial", "0.30.1")

        set("ktx", "1.12.0")
        set("coroutines", "1.7.3")
        set("mockito_core", "5.3.1")
        set("mockito_kotlin", "2.2.0")
        set("testing", "1.1.1")
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
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
