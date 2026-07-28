package org.example.utils.org.example.utils.dto

import kotlinx.serialization.Serializable

@Serializable
data class UploadPinRequest(
    val pinHash: String
)