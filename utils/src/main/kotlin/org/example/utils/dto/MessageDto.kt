package org.example.utils.org.example.utils.dto

import kotlinx.serialization.Serializable


@Serializable
data class MessageDto(
    val id: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val type: String,
    val createdAt: Long,
    val edited: Boolean,
    val deleted: Boolean
)