plugins {
    id("com.android.library")
}

android {
    namespace = "com.mdgd.pokemon.models_impl"
    compileSdk = rootProject.extra["compile"] as Int?

    defaultConfig {
        minSdk = rootProject.extra["min"] as Int?

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        multiDexEnabled = true
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
    implementation(project(":models"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:${rootProject.extra["ver_compat"]}")

    api("com.google.guava:guava:${rootProject.extra["guava"]}")

    // room
    implementation("androidx.room:room-runtime:${rootProject.extra["room"]}")
    annotationProcessor("androidx.room:room-compiler:${rootProject.extra["room_compiler"]}")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:${rootProject.extra["retrofit"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra["retrofit_gson"]}")
    implementation("com.squareup.retrofit2:adapter-rxjava3:${rootProject.extra["retrofit_rx"]}")

    // okHttp
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["okhttp_log"]}")
    implementation("com.squareup.okhttp3:okhttp:${rootProject.extra["okhttp"]}")

    // rx
    implementation("io.reactivex.rxjava3:rxjava:${rootProject.extra["rx_java"]}")
    implementation("io.reactivex.rxjava3:rxandroid:${rootProject.extra["rx_android"]}")
}