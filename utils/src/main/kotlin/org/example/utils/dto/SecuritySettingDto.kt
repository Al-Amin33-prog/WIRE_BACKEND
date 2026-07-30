package org.example.utils.org.example.utils.dto

import kotlinx.serialization.Serializable

@Serializable
data class SecuritySettingsDto(
    val hasPin: Boolean,
    val biometricEnabled: Boolean
)