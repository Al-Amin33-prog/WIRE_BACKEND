package org.example.app.controllers



import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.util.getOrFail
import org.example.app.services.ChatService
import org.example.utils.org.example.utils.dto.ChatActionDto
import org.example.utils.org.example.utils.dto.MessageResponse


class ChatController(
private val chatService: ChatService
) {

    suspend fun sendMessage(call: ApplicationCall) {

        val message = call.receive<ChatActionDto>()

        chatService.processAction(message)

        call.respond(
            HttpStatusCode.OK,
            MessageResponse("Message Sent")
        )
    }

    suspend fun getChatHistory(call: ApplicationCall) {

        val senderId = call.parameters.getOrFail("senderId")
        val receiverId = call.parameters.getOrFail("receiverId")

        val history = chatService.getChatHistory(
                senderId,
        receiverId
        )

        call.respond(history)
    }
    suspend fun getRecentChats(
        call: ApplicationCall
    ){
        val userId = call.parameters.getOrFail("userId")
        val chats = chatService.getRecentChats(userId  )
        call.respond(chats)
    }
}
