package org.example.app.services

interface EmailService {
    suspend fun sendPasswordResetEmail(
        email: String,
        resetLink: String
    )
}