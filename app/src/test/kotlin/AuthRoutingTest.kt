package org.example.app

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.server.routing.*
import org.example.app.auth.FirebaseAuthVerifier
import org.example.app.controllers.AuthController
import org.example.app.plugins.configureSerialization
import org.example.app.routes.authRoutes
import org.example.app.security.AuthVerifier
import org.example.app.services.AuthService
import org.example.utils.org.example.utils.repository.UserRepositoryImpl
import kotlin.test.Test
import kotlin.test.assertEquals

private val repository = UserRepositoryImpl()
private val emailService = FakeEmailService()
private val linkGenerator = FakePasswordResetLinkGenerator()

private val authService = AuthService(
    repository,
    emailService,
    linkGenerator,

)
private val authController = AuthController(authService)
private val verifier: AuthVerifier = FirebaseAuthVerifier()
class AuthRoutingTest {


    @Test
    fun testSyncUserEndpointBlocksMissingAuthorizationHeader() = testApplication {
        // Spin up an isolated, mock Ktor testing context environment
        application {
            configureSerialization()
            routing { // Now correctly resolved
                authRoutes(
                    controller = authController,
                    verifier = verifier,
                ) // Now correctly resolved
            }
        }

        // Simulate an unauthenticated request without a token header
        val response = client.post("/api/auth/sync-user")

        // Assert that the server blocks the request with a 401 Unauthorized status
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun testSyncUserEndpointRejectsMalformedCounterfeitTokens() = testApplication {
        application {
            configureSerialization()
            routing { // Now correctly resolved
                authRoutes(
                    controller = authController,
                    verifier = verifier,
                ) // Now correctly resolved
            }
        }

        // Simulate an attack scenario passing a fake, non-cryptographic JWT token string
        val response = client.post("/api/auth/sync-user") {
            header(HttpHeaders.Authorization, "Bearer counterfeit_malicious_token_string_12345")
        }

        // Assert that your backend pipeline securely flags and drops the fake signature
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }
}
