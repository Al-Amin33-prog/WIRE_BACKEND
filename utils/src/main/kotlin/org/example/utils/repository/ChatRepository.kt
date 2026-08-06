package org.example.utils.org.example.utils.repository

import org.example.utils.org.example.utils.dto.ChatDto


interface ChatRepository {

    suspend fun getRecentChats(
        userId: String
    ): List<ChatDto>

    suspend fun updateChatPreview(
        senderId: String,
        receiverId: String,
        lastMessage: String
    )
}
