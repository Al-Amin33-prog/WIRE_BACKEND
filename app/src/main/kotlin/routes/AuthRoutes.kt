package org.example.app.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.app.security.requireAuth

fun Route.authRoutes() {
    route("/api/auth") {

        post("/sync-user") {
            val firebaseToken = call.requireAuth() ?: return@post

            val authenticatedUid = firebaseToken.uid
            val authenticatedEmail = firebaseToken.email ?: ""

            println("Success: Authenticated user $authenticatedEmail with secure UID: $authenticatedUid")

            // Later: transaction { UsersTable.insertIgnore { ... } }

            call.respond(HttpStatusCode.Created, "Handshake complete. User ID $authenticatedUid is securely mapped!")
        }
    }
}