plugins {
    alias(libs.plugins.android.application)

    // Firebase Google Services Plugin
    id("com.google.gms.google-services") version "4.4.4"
}

android {
    namespace = "com.sheshield.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sheshield.app"
        minSdk = 26
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    // =========================================================
    // ANDROIDX
    // =========================================================

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.fragment)


    // =========================================================
    // GOOGLE LOCATION SERVICES
    // Required for getting user's current GPS location
    // =========================================================

    implementation(libs.play.services.location)


    // =========================================================
    // VOSK OFFLINE SPEECH RECOGNITION
    // =========================================================

    implementation(
        "com.alphacephei:vosk-android:0.3.47"
    )


    // =========================================================
    // FIREBASE BOM
    // =========================================================

    implementation(
        platform(
            "com.google.firebase:firebase-bom:32.8.0"
        )
    )

    // Google Location Services
    implementation("com.google.android.gms:play-services-location:21.4.0")

// Google Places SDK - Nearby Search (New)
    implementation("com.google.android.libraries.places:places:5.0.0")


    // =========================================================
    // GOOGLE ML KIT - ON DEVICE TRANSLATION
    // =========================================================

    implementation(
        "com.google.mlkit:translate:17.0.2"
    )


    // =========================================================
    // FIREBASE AUTHENTICATION - PHONE OTP
    // =========================================================

    implementation(
        "com.google.firebase:firebase-auth"
    )


    // =========================================================
    // UNIT TESTING
    // =========================================================

    testImplementation(
        libs.junit
    )


    // =========================================================
    // ANDROID TESTING
    // =========================================================

    androidTestImplementation(
        libs.ext.junit
    )

    androidTestImplementation(
        libs.espresso.core
    )
}