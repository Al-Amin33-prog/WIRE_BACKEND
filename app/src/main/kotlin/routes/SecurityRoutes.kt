package org.example.app.routes


import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.example.app.controllers.SecurityController
import org.example.app.security.AuthVerifier
import org.example.app.security.requireAuth
import org.example.utils.org.example.utils.dto.UpdateBiometricRequest



fun Route.securityRoutes(
    controller: SecurityController,
    verifier: AuthVerifier
){

    route("/api/security"){
        post("/pin"){
            val firebaseUser = call.requireAuth(
                verifier
            ) ?: return@post
            controller.uploadPin(
                call,
                firebaseUser.uid
            )

        }
        patch("/biometric"){
            val token = call.requireAuth(
               verifier
            ) ?: return@patch
            val request = call.receive<UpdateBiometricRequest>()
            controller.updateBiometric(
                call,
                token,
                request
            )
        }
        get("/setting"){
            val token = call.requireAuth(verifier)?: return@get
            controller.getSecuritySettings(
                call,
                token
            )

        }

    }
}