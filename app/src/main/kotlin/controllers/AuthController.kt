package org.example.app.controllers

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import org.example.app.security.AuthUser
import org.example.app.services.AuthService
import org.example.utils.org.example.utils.dto.ForgotPasswordRequest

class AuthController(
    private val authService: AuthService
) {
    suspend fun syncUser(
        call: ApplicationCall,
        user: AuthUser
    ){
        authService.syncUser(user)

        call.respond(
            HttpStatusCode.Created,
            "User synchronized"
        )
    }
    suspend fun forgotPassword(
        call: ApplicationCall
    ){
        val request = call.receive<ForgotPasswordRequest>()
        authService.forgotPassword(
            request.email
        )
        call.respond(
            HttpStatusCode.OK,
            "If an account exits , a reset link has been sent "
        )
    }
}