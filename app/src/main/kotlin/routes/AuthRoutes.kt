package org.example.app.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.app.security.requireAuth
import org.example.utils.org.example.utils.repository.UserRepositoryImpl

private val repository = UserRepositoryImpl()
fun Route.authRoutes() {

    route("/api/auth") {


        post("/sync-user") {

            val firebaseToken = call.requireAuth() ?: return@post


            val authenticatedUid = firebaseToken.uid
            val authenticatedEmail = firebaseToken.email ?: ""

            repository.upsertUser(
                uid = authenticatedUid,
                email = authenticatedEmail,
                displayName = firebaseToken.name,
                phone = firebaseToken.claims["phone"] as? String
            )

            // Later: transaction { UsersTable.insertIgnore { ... } }

            call.respond(HttpStatusCode.OK, "User synchronized successfully  $authenticatedUid is securely mapped!")
        }
    }
}