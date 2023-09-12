plugins {
    id("com.android.library")
}

android {
    namespace = "com.mdgd.mvi"
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:${rootProject.extra["ver_compat"]}")

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel:${rootProject.extra["lifecycle_version"]}")

    // navigation
    implementation("androidx.navigation:navigation-fragment:${rootProject.extra["nav_version"]}")
    implementation("androidx.navigation:navigation-ui:${rootProject.extra["nav_version"]}")

    // rx
    implementation("io.reactivex.rxjava3:rxjava:${rootProject.extra["rx_java"]}")
    implementation("io.reactivex.rxjava3:rxandroid:${rootProject.extra["rx_android"]}")
}
