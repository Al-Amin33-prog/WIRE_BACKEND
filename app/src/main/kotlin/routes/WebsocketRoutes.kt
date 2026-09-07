package org.example.app.routes

import io.ktor.server.routing.Route
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText

fun Route.chatWebsocketRoutes(){
    webSocket("/ws/chat"){
        println("Websocket client connected")
        try {
            for (frame in incoming){
                if (frame is Frame.Text){
                    val message = frame.readText()
                    println("Received from client")
                    send(Frame.Text("Echo: $message"))
                }
            }
        }finally {
            println("Websocket client disconnected ")
        }
    }
}