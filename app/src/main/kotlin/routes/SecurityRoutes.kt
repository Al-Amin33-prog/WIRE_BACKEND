package org.example.app.routes


import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.example.app.controllers.SecurityController
import org.example.app.security.requireAuth
import org.example.app.services.SecurityApiService
import org.example.utils.org.example.utils.dto.UpdateBiometricRequest
import org.example.utils.org.example.utils.repository.UserRepositoryImpl

private val repository = UserRepositoryImpl()
private val service = SecurityApiService(repository)
private val controller = SecurityController(service)
fun Route.securityRoutes(){
    route("/api/security"){
        post("/pin"){
            val firebaseUser = call.requireAuth() ?: return@post
            controller.uploadPin(
                call,
                firebaseUser.uid
            )

        }
        patch("/biometric"){
            val token = call.requireAuth() ?: return@patch
            val request = call.receive<UpdateBiometricRequest>()
            controller.updateBiometric(
                call,
                token,
                request
            )
        }
        get("/setting"){

        }

    }
}