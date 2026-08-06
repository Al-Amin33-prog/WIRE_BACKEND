package org.example.utils.org.example.utils.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val uid: String,
    val displayName: String?,
    val lastMessage: String,
    val timestamp: Long


)