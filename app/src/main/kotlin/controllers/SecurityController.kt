package org.example.app.controllers


import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import org.example.app.security.AuthUser
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
         user: AuthUser,
          request: UpdateBiometricRequest
    ){
      service.updateBiometric(
          user.uid,
          request.biometricEnabled
      )
        call.respond(HttpStatusCode.OK)
    }
    suspend fun getSecuritySettings(
        call: ApplicationCall,
        user: AuthUser
    ){
        val settings = service.getSecuritySettings(user.uid)
        call.respond(
            HttpStatusCode.OK,
            settings
        )


    }

}