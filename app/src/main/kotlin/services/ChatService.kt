package org.example.app.services



import org.example.utils.org.example.utils.dto.ChatActionDto
import org.example.utils.org.example.utils.dto.ChatDto
import org.example.utils.org.example.utils.dto.MessageDto
import org.example.utils.org.example.utils.repository.ChatRepository
import org.example.utils.org.example.utils.repository.MessageRepository

class ChatService(
private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository
) {
    suspend fun getRecentChats(
        userId: String
    ): List<ChatDto>{
        return chatRepository.getRecentChats(userId)
    }

    suspend fun processAction(action: ChatActionDto) {
        when(action.action){
            "SEND" ->{
                val message = action.message ?: return
                messageRepository.sendMessage(message)
                chatRepository.updateChatPreview(
                    senderId = message.senderId,
                    receiverId = message.receiverId,
                    lastMessage = message.content
                )
            }
            else -> {
                println("Unknown action: ${action.action}")
            }
        }

    }


    suspend fun getChatHistory(
        senderId: String,
        receiverId: String
    ): List<MessageDto> {
        return messageRepository.getChatHistory(
                senderId = senderId,
        receiverId = receiverId
        )
    }
}
