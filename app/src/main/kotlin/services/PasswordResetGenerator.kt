package org.example.app.services

interface PasswordResetLinkGenerator {
    suspend fun generateResetLink(
        email: String
    ): String
}