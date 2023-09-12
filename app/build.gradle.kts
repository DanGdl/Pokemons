plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.mdgd.pokemon"
    compileSdk = rootProject.extra["compile"] as Int?

    defaultConfig {
        applicationId = "com.mdgd.pokemon"
        minSdk = rootProject.extra["min"] as Int
        targetSdk = rootProject.extra["target"] as Int
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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

    // to solve conflicts with kotlin in libraries
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${rootProject.extra["kotlin_version"]}")

    // navigation
    implementation("androidx.navigation:navigation-fragment:${rootProject.extra["nav_version"]}")
    implementation("androidx.navigation:navigation-ui:${rootProject.extra["nav_version"]}")

    // images
    implementation("com.squareup.picasso:picasso:2.71828")

    // json
    implementation("com.google.code.gson:gson:${rootProject.extra["gson"]}")

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel:${rootProject.extra["lifecycle_version"]}")

    // rx
    implementation("io.reactivex.rxjava3:rxjava:${rootProject.extra["rx_java"]}")
    implementation("io.reactivex.rxjava3:rxandroid:${rootProject.extra["rx_android"]}")

    // Java only
    implementation("androidx.work:work-runtime:${rootProject.extra["work_version"]}")

    // multidex
    implementation("androidx.multidex:multidex:2.0.1")

    testImplementation("junit:junit:${rootProject.extra["ver_junit"]}")

    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["junit_android"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["espresso"]}")
}
