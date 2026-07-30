package org.example.app.security



interface AuthVerifier {
    fun verifyToken(token: String): AuthUser?
}