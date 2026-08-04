package org.example.app.plugins



import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.app.auth.FirebaseAuthVerifier
import org.example.app.controllers.AuthController
import org.example.app.controllers.SecurityController
import org.example.app.routes.authRoutes
import org.example.app.routes.securityRoutes
import org.example.app.security.AuthVerifier
import org.example.app.services.AuthService
import org.example.app.services.FirebasePasswordResetLinkGenerator
import org.example.app.services.ResendEmailService
import org.example.app.services.SecurityApiService
import org.example.utils.org.example.utils.repository.UserRepositoryImpl

fun Application.configureRouting() {
    val emailService = ResendEmailService()
    val linkGenerator = FirebasePasswordResetLinkGenerator()
    val repository = UserRepositoryImpl()
    val verifier: AuthVerifier = FirebaseAuthVerifier()
    val authService = AuthService(
        repository,
        emailService,
        linkGenerator
    )
    val authController = AuthController(authService)
    val securityService = SecurityApiService(repository)
    val securityController = SecurityController(securityService)
    routing {
        get("/health") {
            call.respondText("Wire backend is running")
        }

        authRoutes(
            controller = authController,
            verifier = verifier,

        )
        securityRoutes(
            controller = securityController,
            verifier = verifier
        )
    }
}