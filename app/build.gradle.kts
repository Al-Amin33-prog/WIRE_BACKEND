plugins {
    // Apply the shared build logic from your convention plugin.
    id("kotlin-jvm")

    // Apply the Application plugin to make this an executable JVM app.
    application

    // Apply the Ktor and Kotlin Serialization plugins from your version catalog
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinPluginSerialization)
}

dependencies {
    // Links your main app to your database and utility layers
    implementation(project(":utils"))

    // Ktor web framework bundle (Core, Netty, WebSockets, CORS, Auth, etc.)
    implementation(libs.bundles.ktor)

    // Logging engine required by Ktor/Netty to output logs to your console
    implementation(libs.logback)

    // Third-party API service integrations
    implementation(libs.firebase.admin)
    implementation(libs.stripe)

    // Ktor test suite dependencies
    testImplementation(libs.ktor.server.test.host)
    testImplementation(kotlin("test"))
}

application {
    // Defines the main entry point class of your backend server
    mainClass.set("org.example.app.AppKt")
}
