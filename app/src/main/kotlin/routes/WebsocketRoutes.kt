package org.example.app.routes

import io.ktor.server.routing.Route
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.app.services.ChatService
import org.example.utils.org.example.utils.dto.ChatActionDto
import org.example.utils.org.example.utils.dto.MessageDto
import java.util.UUID

fun Route.chatWebsocketRoutes(
    chatService: ChatService
) {
    webSocket("/ws/chat") {

        println("Websocket client connected")

        try {
            for (frame in incoming) {

                if (frame is Frame.Text) {

                    val json = frame.readText()

                    println("Received from client: $json")

                    try {
                        val action =
                            Json.decodeFromString<ChatActionDto>(json)

                        when (action.action) {

                            "SEND" -> {
                                val message = action.message
                                    ?: continue

                                // Persist the user's message
                                chatService.processAction(action)

                                // Create assistant response
                                val assistantMessage =
                                    MessageDto(
                                        id = UUID.randomUUID().toString(),
                                        senderId = message.receiverId,
                                        receiverId = message.senderId,
                                        content = "Echo: ${message.content}",
                                        type = message.type,
                                        createdAt = System.currentTimeMillis(),
                                        edited = false,
                                        deleted = false
                                    )

                                val response =
                                    ChatActionDto(
                                        action = "SEND",
                                        message = assistantMessage
                                    )

                                // Persist assistant response too
                                chatService.processAction(response)

                                // Send response back to Android
                                send(
                                    Frame.Text(
                                        Json.encodeToString(response)
                                    )
                                )
                            }

                            else -> {
                                println(
                                    "Unknown WebSocket action: ${action.action}"
                                )
                            }
                        }

                    } catch (e: Exception) {
                        println(
                            "WebSocket processing error: ${e.message}"
                        )
                        e.printStackTrace()
                    }
                }
            }

        } finally {
            println("Websocket client disconnected")
        }
    }
}