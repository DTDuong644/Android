plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.tlu_rideshare" // Đảm bảo đúng namespace của ứng dụng của bạn
    compileSdk = 34 // Phiên bản SDK mục tiêu (target SDK)

    defaultConfig {
        applicationId = "com.example.tlu_rideshare" // ID ứng dụng duy nhất của bạn
        minSdk = 24 // Phiên bản Android tối thiểu mà ứng dụng hỗ trợ (ví dụ: Android 7.0 Nougat)
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8" // Chỉ định JVM target cho Kotlin code (thường là 1.8)
    }
}

dependencies {
    // AndroidX Core và các thành phần UI cơ bản
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase BoM (Bill of Materials) để quản lý phiên bản các thư viện Firebase.
    // Luôn sử dụng phiên bản mới nhất từ: https://firebase.google.com/docs/android/setup#available-libraries
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))

    // Các thư viện Firebase cụ thể (không cần chỉ định phiên bản riêng khi dùng BoM)
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx") // Thêm hỗ trợ Phone Auth và Kotlin
    implementation("com.google.firebase:firebase-firestore-ktx")


    // Các thư viện cho EdgeToEdge và SplashScreen
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Thư viện kiểm thử
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}