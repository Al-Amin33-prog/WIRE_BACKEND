package org.example.app.controllers

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.example.app.security.AuthUser
import org.example.app.services.AuthService

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
}