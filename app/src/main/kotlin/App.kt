package org.example.app


import io.ktor.server.application.Application
import org.example.utils.org.example.utils.db.DatabaseFactory
import org.example.app.config.FirebaseAdmin // Import your Firebase Config
import org.example.app.routes.authRoutes     // Import your Auth Routes

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.* // Import Ktor routing extension
import org.example.app.plugins.configureRouting

fun main() {
    println("Starting Wire Backend Engine...")

    // 1. Initialize your PostgreSQL & Hikari Connection Pool from the :utils module
    try {
        DatabaseFactory.init()
        println("Database connected successfully!")
    } catch (e: Exception) {
        println("CRITICAL: Database connection failed: ${e.message}")
        e.printStackTrace()
    }

    // 2. Initialize your Firebase Admin SDK secure environment
    try {
        FirebaseAdmin.init()
    } catch (e: Exception) {
        println("CRITICAL: Firebase Admin initialization failed: ${e.message}")
        e.printStackTrace()
    }

    // 3. Start your Ktor Netty Server
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        // Plug your route controllers into the Ktor module tree
        routing {
            authRoutes()
        }
    }.start(wait = true)
    fun Application.module(){
        configureRouting()
    }
}
