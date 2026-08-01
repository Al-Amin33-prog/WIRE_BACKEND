package org.example.app

import org.example.app.services.EmailService

class FakeEmailService : EmailService{
    var lastEmail: String? = null
        private set
    var lastResetLink: String? = null
            private set
    var sendCount = 0
    override suspend fun sendPasswordResetEmail(email: String, resetLink: String) {
        lastEmail = email
        lastResetLink = resetLink
        sendCount++

    }

}