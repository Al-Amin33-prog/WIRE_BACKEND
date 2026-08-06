package org.example.utils.org.example.utils.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatActionDto(
    val action: String,
    val message: MessageDto? = null,
    val messageId: String? = null
)
