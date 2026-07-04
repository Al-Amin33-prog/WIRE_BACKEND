package org.example.app.security



import com.google.firebase.auth.FirebaseToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.example.app.auth.TokenVerifier

/**
 * Call this at the top of any protected route.
 * Returns the verified FirebaseToken if valid, or responds 401 and returns null.
 */
suspend fun ApplicationCall.requireAuth(): FirebaseToken? {
    val authHeader = request.headers["Authorization"]

    if (authHeader == null) {
        respond(HttpStatusCode.Unauthorized, "Missing Authorization Header")
        return null
    }

    val firebaseToken = TokenVerifier.verifyToken(authHeader)

    if (firebaseToken == null) {
        respond(HttpStatusCode.Unauthorized, "Invalid or Expired Token Signature")
        return null
    }

    return firebaseToken
}

