package org.example.app.security




import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*


/**
 * Call this at the top of any protected route.
 * Returns the verified FirebaseToken if valid, or responds 401 and returns null.
 */
suspend fun ApplicationCall.requireAuth(
    verifier: AuthVerifier
): AuthUser? {
    val authHeader = request.headers["Authorization"]

    if (authHeader == null) {
        respond(HttpStatusCode.Unauthorized, "Missing Authorization Header")
        return null
    }
    val authUser = verifier.verifyToken(authHeader)
    if (authUser == null){
        respond(
            HttpStatusCode.Unauthorized,
            "Invalid or Expired Token Signature"
        )
        return null
    }
    return authUser


}

