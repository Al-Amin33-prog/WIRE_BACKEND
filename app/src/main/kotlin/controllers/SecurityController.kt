package org.example.app.controllers

import com.google.firebase.auth.FirebaseToken
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import org.example.app.services.SecurityApiService
import org.example.utils.org.example.utils.dto.UpdateBiometricRequest
import org.example.utils.org.example.utils.dto.UploadPinRequest

class SecurityController(
    private val service: SecurityApiService
) {
    suspend fun uploadPin(
        call: ApplicationCall,
        uid: String
    ){
        val request = call.receive<UploadPinRequest>()
        service.savePin(
            uid = uid,
            pinHash = request.pinHash
        )
        call.respond(
            HttpStatusCode.OK,
            "PIN saved successfully"
        )
    }
    suspend fun updateBiometric(
        call: ApplicationCall,
        token: FirebaseToken,
        request: UpdateBiometricRequest
    ){
      service.updateBiometric(
          token.uid,
          request.biometricEnabled
      )
        call.respond(HttpStatusCode.OK)
    }

}