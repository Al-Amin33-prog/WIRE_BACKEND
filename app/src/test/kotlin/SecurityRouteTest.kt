package org.example.app

import io.ktor.server.routing.routing
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import org.example.app.controllers.SecurityController
import org.example.app.plugins.configureSerialization
import org.example.app.routes.securityRoutes
import org.example.app.services.SecurityApiService
import kotlin.test.Test
import kotlin.test.assertEquals


private val repository = FakeUserRepository()
private val service = SecurityApiService(repository)
private val controller = SecurityController(service)
private val verifier = FakeAuthVerifier()
class SecurityRouteTest {
    @Test
    fun `POST security without token`() = testApplication {
        application {
            configureSerialization()
           routing {
               securityRoutes(
                   controller = controller,
                   verifier = verifier
               )
           }
        }
        val response = client.post("/api/security/pin"){
            contentType(ContentType.Application.Json)
            setBody(
                """
                    {
                    "pinHash":"abcdef123456
                    }
                """.trimIndent()
            )
        }
        println(response.status)
        println(response.bodyAsText())
        assertEquals(HttpStatusCode.Unauthorized, response.status)

    }
    @Test
   fun  `POST security pin stores hashed pin  `() = testApplication {
        application {
            configureSerialization()
            routing {
                securityRoutes(
                    controller = controller,
                    verifier = verifier
                )
            }
        }
        val response = client.post("/api/security/pin"){
            header(
                HttpHeaders.Authorization,
                "Bearer TEST_TOKEN"
            )
            contentType(ContentType.Application.Json)
            setBody(
                """
                    {
                    "pinHash" : "HASH_123456789"
                    }                    
                """
                    .trimIndent()
            )
        }
        println(response.status)
        println(response.bodyAsText())
        assertEquals(
            HttpStatusCode.OK,
            response.status
        )
    }
    @Test
    fun` PATCH biometric updates status`() = testApplication {
        application {
            configureSerialization()
            routing {
                securityRoutes(
                    controller = controller,
                    verifier = verifier
                )
            }
        }
        val response = client.patch("/api/security/biometric"){
            header(
                HttpHeaders.Authorization,
                "Bearer TEST_TOKEN"
            )
            contentType(ContentType.Application.Json)
            setBody(
                """
                    {
                    "biometricEnabled":true
                    }                    
                """.trimIndent()
            )
        }

        println(response.status)
        println(response.bodyAsText())
        assertEquals(
            HttpStatusCode.OK,
            response.status
        )
    }
    @Test
    fun`GET security settings returns user settings`() = testApplication {
        application {
            configureSerialization()
            routing {
                securityRoutes(
                    controller = controller,
                    verifier = verifier
                )
            }
        }
        val response = client.get("/api/security/setting"){
            header(
                HttpHeaders.Authorization,
                "Bearer TEST_TOKEN"
            )
        }
        println(response.status)
        println(response.bodyAsText())
        assertEquals(
            HttpStatusCode.OK,
            response.status
        )

    }
}