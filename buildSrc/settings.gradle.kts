pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
    // Ensures internal plugin generation tasks can look up dependencies
    repositories {
        mavenCentral()
    }
}

rootProject.name = "buildSrc"
