package org.example.app.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.example.app.controllers.AuthController
import org.example.app.security.AuthVerifier
import org.example.app.security.requireAuth




fun Route.authRoutes(
    controller: AuthController,
    verifier: AuthVerifier

) {

    route("/api/auth") {


        post("/sync-user") {

            val firebaseToken = call.requireAuth(
                verifier
            ) ?: return@post
            controller.syncUser(
                call,
                firebaseToken
            )

        }
    }
}