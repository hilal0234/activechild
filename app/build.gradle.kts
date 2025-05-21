plugins {
    id("com.android.application")
    id("com.google.gms.google-services") // Firebase için
}

android {
    namespace = "com.example.activechild"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.activechild"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    // CardView
    implementation("androidx.cardview:cardview:1.0.0")

    // Firebase
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-core:21.1.1")
}
