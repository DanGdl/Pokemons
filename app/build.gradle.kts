plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.mdgd.pokemon"
    compileSdk = rootProject.extra["compile"] as Int?

    defaultConfig {
        applicationId = "com.mdgd.pokemon"
        minSdk = rootProject.extra["min"] as Int?

        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":mvi"))
    implementation(project(":models"))
    implementation(project(":models_impl"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // swipe-refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.19.0")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["nav_version"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["nav_version"]}")

    // image loading
    implementation("io.coil-kt:coil-compose:1.3.0")

    // json
    implementation("com.google.code.gson:gson:${rootProject.extra["gson"]}")

    // hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hilt"]}")
    ksp("com.google.dagger:hilt-android-compiler:${rootProject.extra["hilt"]}")

    implementation("androidx.hilt:hilt-navigation-fragment:${rootProject.extra["hilt_jetpack"]}")
    ksp("androidx.hilt:hilt-compiler:${rootProject.extra["hilt_jetpack"]}")
    implementation("androidx.hilt:hilt-work:1.0.0")

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycle_ktx"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycle_ktx"]}")

    implementation("androidx.work:work-runtime-ktx:${rootProject.extra["work_version"]}")

    testImplementation("junit:junit:${rootProject.extra["ver_junit"]}")
    testImplementation("org.mockito:mockito-core:${rootProject.extra["mockito_core"]}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${rootProject.extra["mockito_kotlin"]}")
    testImplementation("android.arch.core:core-testing:${rootProject.extra["testing_core"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra["testing_coroutine"]}")
    testImplementation("com.google.code.gson:gson:${rootProject.extra["gson"]}")

    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["junit_android"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["espresso"]}")
    androidTestImplementation("com.android.support.test.espresso:espresso-contrib:3.3.0")

    implementation("androidx.core:core-ktx:${rootProject.extra["ktx"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutines"]}")
}
