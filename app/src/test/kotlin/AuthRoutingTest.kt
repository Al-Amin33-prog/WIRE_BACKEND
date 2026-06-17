package org.example.app

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.server.routing.*
import org.example.app.routes.authRoutes
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthRoutingTest {

    @Test
    fun testSyncUserEndpointBlocksMissingAuthorizationHeader() = testApplication {
        // Spin up an isolated, mock Ktor testing context environment
        application {
            routing { // Now correctly resolved
                authRoutes() // Now correctly resolved
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
            routing { // Now correctly resolved
                authRoutes() // Now correctly resolved
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
