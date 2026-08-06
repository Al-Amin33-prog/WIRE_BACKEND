package org.example.app.routes





import io.ktor.server.application.call
import io.ktor.server.routing.*
import org.example.app.controllers.ChatController

fun Route.chatRoutes(
controller: ChatController
) {

    route("/chat") {

        post("/send") {
        controller.sendMessage(call)
    }

        get("/history/{senderId}/{receiverId}") {
        controller.getChatHistory(call)
    }
        get("/recent/{userId}") {

            controller.getRecentChats(call)

        }

    }
}
