package org.example.app.security

data class AuthUser(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val phone: String?
)