plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "dev.pack_my_trip"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.pack_my_trip"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}



dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.osmdroid:osmdroid-android:6.1.17")
    implementation("com.google.maps:google-maps-services:0.15.0")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.google.android.libraries.places:places:3.2.0")
    implementation("androidx.compose.ui:ui-graphics-android:1.5.3")
    implementation("com.android.volley:volley:1.2.1")
    implementation ("com.squareup.retrofit2:retrofit:2.7.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation("com.dmitryborodin:pdfview-android:1.1.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.firebase:firebase-storage:20.0.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}