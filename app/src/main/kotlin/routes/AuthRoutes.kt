package org.example.app.routes



import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.app.auth.TokenVerifier


fun Route.authRoutes() {
    route("/api/auth") {

        // This is the endpoint Android calls using: POST http://<YOUR_IP>:8080/api/auth/sync-user
        post("/sync-user") {
            val authHeader = call.request.headers["Authorization"]

            if (authHeader == null) {
                call.respond(HttpStatusCode.Unauthorized, "Missing Authorization Header")
                return@post
            }

            // Verify using your new TokenVerifier
            val firebaseToken = TokenVerifier.verifyToken(authHeader)
            if (firebaseToken == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid or Expired Token Signature")
                return@post
            }

            // Google guarantees this data is 100% genuine
            val authenticatedUid = firebaseToken.uid
            val authenticatedEmail = firebaseToken.email ?: ""

            println("Success: Authenticated user $authenticatedEmail with secure UID: $authenticatedUid")

            // Later on, you will run your Exposed ORM insertion query right here:
            // transaction { UsersTable.insertIgnore { ... } }

            call.respond(HttpStatusCode.Created, "Handshake complete. User ID $authenticatedUid is securely mapped!")
        }
    }
}
