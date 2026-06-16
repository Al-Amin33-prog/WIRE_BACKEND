plugins {
    // Apply the shared build logic from a convention plugin.
    // The shared code is located in `buildSrc/src/main/kotlin/kotlin-jvm.gradle.kts`.
    id("kotlin-jvm")
    // Apply Kotlin Serialization plugin from `gradle/libs.versions.toml`.
    alias(libs.plugins.kotlinPluginSerialization)
}

dependencies {
    // Apply the kotlinx bundle of dependencies from the version catalog (`gradle/libs.versions.toml`).
    implementation(libs.bundles.kotlinxEcosystem)
    implementation(libs.dotenv)

    // Database connection stack (Exposed ORM, Hikari connection pooling, PostgreSQL driver)
    implementation(libs.bundles.exposed)
    implementation(libs.postgresql)
    implementation(libs.hikari)

    testImplementation(kotlin("test"))
}