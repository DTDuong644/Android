pluginManagement {
    repositories {
        google() // Đảm bảo KHÔNG CÓ PHẦN content { ... } ở đây. Đây là thay đổi quan trọng.
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TLURideShare"
include(":app")