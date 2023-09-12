plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
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
    implementation(project(":adapter"))
    implementation(project(":models"))
    implementation(project(":models_impl"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.appcompat:appcompat:${rootProject.extra["ver_compat"]}")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:${rootProject.extra["recycler"]}")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.google.android.material:material:1.9.0")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["nav_version"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["nav_version"]}")

    // images
    implementation("com.squareup.picasso:picasso:2.71828")

    // json
    implementation("com.google.code.gson:gson:${rootProject.extra["gson"]}")

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycle_ktx"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycle_ktx"]}")

    implementation("androidx.work:work-runtime-ktx:${rootProject.extra["work_version"]}")

    // kotlin
    implementation("androidx.core:core-ktx:${rootProject.extra["ktx"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutines"]}")

    testImplementation("junit:junit:${rootProject.extra["ver_junit"]}")
    testImplementation("org.mockito:mockito-core:${rootProject.extra["mockito_core"]}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${rootProject.extra["mockito_kotlin"]}")
    testImplementation("android.arch.core:core-testing:${rootProject.extra["testing"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra["coroutines"]}")
    testImplementation("com.google.code.gson:gson:${rootProject.extra["gson"]}")

    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["junit_android"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["espresso"]}")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("com.android.support.test.espresso:espresso-contrib:3.3.0")
}
