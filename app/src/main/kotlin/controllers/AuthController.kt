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
    suspend fun forgotPassword(call: ApplicationCall) {
        try {
            val request = call.receive<ForgotPasswordRequest>()
            authService.forgotPassword(request.email)
            println("STEP 6 - ABOUT TO RESPOND")
            call.respond(HttpStatusCode.OK, mapOf("status" to "success"))
            println("STEP 7 RESPONSE SENT")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
        }
    }
}