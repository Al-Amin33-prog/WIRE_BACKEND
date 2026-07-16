package org.example.app.controllers

import com.google.firebase.auth.FirebaseToken
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.example.app.services.AuthApiService

class AuthController(
    private val authApiService: AuthApiService
) {
    suspend fun syncUser(
        call: ApplicationCall,
        token: FirebaseToken
    ){
        authApiService.syncUser(
            token.uid,
            token.email ?: ""
        )
        call.respond(
            HttpStatusCode.Created,
            "User synchronized"
        )
    }
}