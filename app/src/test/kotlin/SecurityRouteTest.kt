package org.example.app

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
import kotlin.test.Test
import kotlin.test.assertEquals

class SecurityRouteTest {
    @Test
    fun `POST security without token`() = testApplication {
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