package org.example.app.plugins



import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.app.routes.authRoutes
import org.example.app.routes.securityRoutes

fun Application.configureRouting() {
    routing {
        get("/health") {
            call.respondText("Wire backend is running")
        }

        authRoutes()
        securityRoutes()
    }
}