package org.example.app

import io.ktor.server.testing.testApplication
import org.example.app.services.AuthService
import kotlin.test.Test
import kotlin.test.assertEquals


private val repository = FakeUserRepository()
private val emailService = FakeEmailService()
private val linkGenerator = FakePasswordResetLinkGenerator()
private val authService = AuthService(
    repository,
    emailService,
    linkGenerator
)
class AuthServiceTest {
     @Test
     fun`forgot password sends reset email for registered user`() = testApplication {
         authService.forgotPassword(
             "test@example.com"
         )
         assertEquals(
             1,
             emailService.sendCount
         )
         assertEquals(
             "test@example.com",
             emailService.lastEmail
         )
         assertEquals(
             "https://fake-reset-link.com/reset?email=test@example.com",
             emailService.lastResetLink
         )
     }
    @Test
    fun `forgot password does nothing for unknown email`() = testApplication {
        authService.forgotPassword(
            "unknown@example.com"
        )
        assertEquals(
            0,
            emailService.sendCount
        )
    }

}