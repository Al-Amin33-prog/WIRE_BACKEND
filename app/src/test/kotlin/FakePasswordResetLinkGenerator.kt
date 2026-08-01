package org.example.app

import org.example.app.services.PasswordResetLinkGenerator

class FakePasswordResetLinkGenerator : PasswordResetLinkGenerator{
    override suspend fun generateResetLink(email: String): String {
        return "https://fake-reset-link.com/reset?email=$email"
    }

}