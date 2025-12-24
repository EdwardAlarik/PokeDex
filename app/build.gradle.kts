plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.edwardalarik.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.edwardalarik.pokedex"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    flavorDimensions.add("flavor")

    productFlavors {
        create("DEV") {
            dimension = "flavor"

            buildConfigField("String", "app_name", "\"PokeDex DEV\"")
            buildConfigField(
                "String",
                "url_app",
                "\"https://pokeapi.co/api/v2/\""
            )
        }
        create("PROD") {
            dimension = "flavor"

            buildConfigField("String", "app_name", "\"PokeDex\"")
            buildConfigField(
                "String",
                "url_app",
                "\"https://pokeapi.co/api/v2/\""
            )
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.vision)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.material.v1120)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.glide)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Lifecycle components
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.reactivestreams.ktx)
    implementation(libs.androidx.lifecycle.extensions)

    // Navigation library
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    // ImageView optimization
    implementation(libs.glide)
    implementation("com.github.bumptech.glide:recyclerview-integration:4.16.0") {
        isTransitive = false
    }

    // Animations
    implementation(libs.lottie)

    // Alerter
    implementation(libs.alerter)

    // WorkManager
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.work.runtime.ktx.v291)
    implementation(libs.androidx.work.rxjava2.v291)
    implementation(libs.androidx.work.gcm.v291)
    androidTestImplementation(libs.androidx.work.testing.v291)
    implementation(libs.androidx.work.multiprocess.v291)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.hilt.navigation.fragment)
    kapt(libs.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    annotationProcessor(libs.androidx.hilt.compiler.v100)

    // Cipher Database
    implementation(libs.sqlcipher.android)
    implementation(libs.androidx.sqlite.ktx)
}

kapt {
    correctErrorTypes = true
}