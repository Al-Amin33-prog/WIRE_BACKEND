package org.example.app.services

import com.google.firebase.auth.FirebaseAuth

class FirebasePasswordResetLinkGenerator: PasswordResetLinkGenerator {
    override suspend fun generateResetLink(email: String): String {
        return FirebaseAuth
            .getInstance()
            .generatePasswordResetLink(email)
    }
}