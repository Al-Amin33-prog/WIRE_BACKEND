package org.example.utils.org.example.utils.repository


import org.example.utils.org.example.utils.dto.MessageDto

interface MessageRepository {


    suspend fun sendMessage(
        message: MessageDto
    )

    suspend fun getChatHistory(
        senderId: String,
        receiverId: String
    ): List<MessageDto>

}