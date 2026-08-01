package org.example.utils.org.example.utils.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordRequest (
    val email: String
)