package org.example.app.services

class FirebaseEmailService : EmailService{
    override suspend fun sendPasswordResetEmail(email: String, resetLink: String) {
        println(
            """
                ==========================
                Password Reset Email 
                To:$email
                Reset Link: $resetLink
                ==========================
            """.trimIndent()
        )
    }

}