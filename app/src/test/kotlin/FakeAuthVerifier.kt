package org.example.app


import org.example.app.security.AuthUser
import org.example.app.security.AuthVerifier



class FakeAuthVerifier : AuthVerifier {
    override fun verifyToken(token: String): AuthUser? {
        if (token.removePrefix("Bearer").trim() != "TEST_TOKEN")
            return null
        return AuthUser(
            uid = "test_uid",
            email = "test@example.com",
            displayName = "Test User",
            phone = null
        )
    }
}