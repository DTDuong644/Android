// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

// Không cần thêm repositories ở đây vì đã định nghĩa trong settings.gradle.kts
