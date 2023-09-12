plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.mdgd.pokemon.models_impl"
    compileSdk = rootProject.extra["compile"] as Int?

    defaultConfig {
        minSdk = rootProject.extra["min"] as Int?

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(project(":models"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:${rootProject.extra["ver_compat"]}")

    // room
    implementation("androidx.room:room-runtime:${rootProject.extra["room"]}")
    ksp("androidx.room:room-compiler:${rootProject.extra["room_compiler"]}")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:${rootProject.extra["retrofit"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra["retrofit_gson"]}")

    // okHttp
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["okhttp_log"]}")
    implementation("com.squareup.okhttp3:okhttp:${rootProject.extra["okhttp"]}")

    implementation("androidx.core:core-ktx:${rootProject.extra["ktx"]}")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutines"]}")

    testImplementation("junit:junit:${rootProject.extra["ver_junit"]}")
    testImplementation("org.mockito:mockito-core:${rootProject.extra["mockito_core"]}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${rootProject.extra["mockito_kotlin"]}")
    testImplementation("android.arch.core:core-testing:${rootProject.extra["testing"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra["coroutines"]}")
    testImplementation("com.google.code.gson:gson:${rootProject.extra["gson"]}")
}
