// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Luôn sử dụng phiên bản Android Gradle Plugin mới nhất ổn định.
    // Kiểm tra tại: https://developer.android.com/studio/releases/gradle-plugin
    id("com.android.application") version "8.2.0" apply false

    // Phiên bản của Kotlin plugin. Đảm bảo nó tương thích với Kotlin version trong dependencies
    // và phiên bản Android Gradle Plugin.
    // Kiểm tra tại: https://kotlinlang.org/docs/gradle.html#apply-plugins
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false // Đổi 1.9.0 nếu bạn dùng phiên bản khác

    // Phiên bản của Google Services plugin (cho Firebase).
    // Kiểm tra tại: https://developers.google.com/android/guides/google-services-plugin
    id("com.google.gms.google-services") version "4.4.1" apply false // Đổi 4.4.1 nếu có phiên bản mới hơn
}